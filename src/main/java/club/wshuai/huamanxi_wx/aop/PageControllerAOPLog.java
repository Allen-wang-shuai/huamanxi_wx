package club.wshuai.huamanxi_wx.aop;

import club.wshuai.huamanxi_wx.domain.AccessLog;
import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.domain.User;
import club.wshuai.huamanxi_wx.entity.Headers;
import club.wshuai.huamanxi_wx.entity.MyJson;
import club.wshuai.huamanxi_wx.service.AopService;
import club.wshuai.huamanxi_wx.service.AuthorService;
import club.wshuai.huamanxi_wx.service.PoemService;
import club.wshuai.huamanxi_wx.utils.HttpRequest;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
@Aspect
public class PageControllerAOPLog {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AopService aopService;
    @Autowired
    private PoemService poemService;
    @Autowired
    private AuthorService authorService;

    //访问的时间
    private Long visitTime;
    //访问的类
    private Class aClass;
    //访问的方法
    private Method method;
    //请求参数
    private String parameter = "";


    //下面的两个参数要注意这是全局的发送完之后记得还原数据为"null"否则其值不会消失！！！
    //poemId
    private String poemId = "null";
    //authorId
    private String authorId = "null";

    //切入点表达式
    @Pointcut("execution(* club.wshuai.huamanxi_wx.controller.PageController.toAuthorDetails(..))" +
            "||execution(* club.wshuai.huamanxi_wx.controller.PageController.toPoemDetails(..)) " +
            "||execution(* club.wshuai.huamanxi_wx.controller.PageController.collectPoem(..)) " +
            "||execution(* club.wshuai.huamanxi_wx.controller.PageController.collectAuthor(..))")
    private void pointCut(){}

    /**
     * 前置通知
     * @param joinPoint
     * @throws Exception
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint)throws Exception{
        //当前时间就是开始访问的时间
        visitTime = new Date().getTime();
        //该用户访问的类
        aClass = joinPoint.getTarget().getClass();
        //获取访问方法的名称
        String methodName = joinPoint.getSignature().getName();
        //获取访问的方法的参数对象，这里要注意以后使用反射获取参数对象的数组时，如果参数是基本
        //类型要用其包装类，不然获取不到该参数的对象
        Object[] args = joinPoint.getArgs();

        //获取请求参数
        if (request.getParameterMap()!=null){
            parameter = "?";
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<String> keySet = parameterMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                parameter = parameter+key+"=";
                String[] strings = parameterMap.get(key);
                for (String string : strings) {
                    parameter = parameter+string;
                    if (key.equals("poemId")){
                        poemId = string;
                    }
                    if (key.equals("authorId")){
                        authorId = string;
                    }
                }
                parameter = parameter+"&";
            }
            parameter = parameter.substring(0,parameter.length()-1);
//            System.out.println(parameter);
        }

        //获取具体执行的方法的Method对象
        if (args == null || args.length == 0){
            //获取的无参数的方法对象
            method = aClass.getMethod(methodName);
        }else {
            //参数的Class对象数组
            Class[] classArgs = new Class[args.length];
            //为classArgs赋值
            for (int i = 0;i < args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            //获取带参数的方法对象
            method = aClass.getMethod(methodName,classArgs);
        }
    }

    /**
     * 后置通知
     * @param joinPoint
     * @throws Exception
     */
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint)throws Exception{
        //操作时长
        long executionTime = System.currentTimeMillis()-visitTime;

        //访问的URL
        String url = "";

        //通过反射获取访问的url
        if (aClass!=null&&method!=null){
            //1.获取访问的类上的@RequestMapping(xxx)注解
            RequestMapping classAnnotation = (RequestMapping) aClass.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classAnnoValue = classAnnotation.value();
                //2.获取方法上的@RequestMapping(xxx)注解
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodAnnoValue = methodAnnotation.value();
                    //classAnnoValue和methodAnnoValue虽然是数组但实际上值就有一个，不信自己看
                    //访问的url
                    url = classAnnoValue[0]+methodAnnoValue[0];

                    //获取访问的ip
                    String ip = request.getRemoteAddr();

                    //获取当前操作的用户
                    User user = (User) request.getSession().getAttribute("UserInformation");

                    //创建日志对象并设置值
                    AccessLog accessLog = new AccessLog();
                    accessLog.setVisitTime(visitTime);
                    accessLog.setUserId(user.getUserId());
                    accessLog.setUsername(user.getUsername());
                    accessLog.setAccessIP(ip);
                    accessLog.setUrl(url);
                    accessLog.setParameter(parameter);
                    accessLog.setExecutionTime(executionTime);
                    accessLog.setMethod("[类名]"+aClass.getName()+"[方法名]"+method.getName());

                    //保存日志
                    aopService.save(accessLog);

                    MyJson json = new MyJson();
                    Headers headers = new Headers();
                    if (!poemId.equals("null")){
                        Poem poem = poemService.findPoemById(Integer.parseInt(poemId));
                        if (poem.getPoemTagNames()!=null&&!poem.getPoemTagNames().equals("null")){
                            poem.setPoemTagNames(poem.getPoemTagNames().replaceAll(",","-"));
                        }
                        headers.setUserId(user.getUserId());
                        headers.setTags(poem.getPoemId()+"-"+String.valueOf(poem.getPoemAuthorId()+100000)+"-"+poem.getPoemDynasty()+"-"+poem.getPoemTagNames());
                        json.setHeaders(headers);
                        json.setBody(accessLog.toString());
                        JSONObject jsonObject = JSONObject.fromObject(json);
                        String s = jsonObject.toString();
                        s = "["+s+"]";
//                        System.out.println(s);
                        HttpRequest.httpPost("http://120.26.89.198:5555",s,true);
                        //下面这个很重要记得将数据还原
                        poemId = "null";
                    }

                    if (!authorId.equals("null")){
                        Author author = authorService.findAuthorById(Integer.parseInt(authorId));
                        headers.setUserId(user.getUserId());
                        headers.setTags("NoPoemID"+"-"+String.valueOf(Integer.parseInt(authorId)+100000)+"-"+author.getAuthorDynasty());
                        json.setHeaders(headers);
                        json.setBody(accessLog.toString());
                        JSONObject jsonObject = JSONObject.fromObject(json);
                        String s = jsonObject.toString();
                        s = "["+s+"]";
//                        System.out.println(s);
                        HttpRequest.httpPost("http://120.26.89.198:5555",s,true);
                        //下面这个很重要记得将数据还原
                        authorId = "null";
                    }

                }
            }
        }
    }
}
