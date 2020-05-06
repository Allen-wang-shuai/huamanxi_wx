package club.wshuai.huamanxi_wx.interceptor;

import club.wshuai.huamanxi_wx.domain.User;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CentreInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User UserInformation = (User) request.getSession().getAttribute("UserInformation");

        if (UserInformation == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
//            System.out.println("没登陆！");
//            JSONObject res = new JSONObject();
//            res.put("login","false");
            outputStream.print(false);
            outputStream.flush();
            return false;
        }else {
            return true;
        }
    }

}
