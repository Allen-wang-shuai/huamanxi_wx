package club.wshuai.huamanxi_wx.controller;

import club.wshuai.huamanxi_wx.domain.User;
import club.wshuai.huamanxi_wx.service.UserService;
import club.wshuai.huamanxi_wx.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loginRegisterController")
@Api(value = "登陆注册相关接口", description = "提供用户的登录、注册、退出等功能")
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    /**
     * 异步校验用户名是否存在
     *
     * @param username 需要的参数名：username  用户输入的用户名
     * @return 返回的是true代表该用户名可用，返回的是false代表该用户名已存在不可用
     * @throws Exception 只要有异常就抛出
     */
    @GetMapping("/checkUsername")
    @ApiOperation("异步校验用户名是否存在")
    @ApiImplicitParam(name = "username",value = "用户名(必填)",required = true)
    public boolean checkUsername(String username) throws Exception {
//        System.out.println("异步校验用户名！");
        boolean isExist = userService.isExist(username);
        return isExist;
    }


    /**
     * 用户注册，采用post提交方式
     * @param user 需要的是一个user对象所包含的参数名分别是：
     *             username  userPassword  userEmail  userSex
     *
     * @return 返回true
     * @throws Exception
     */
    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "user",value = "用户基本信息，不需要填写用户Id，后台统一设置")
    public boolean userRegister(@RequestBody User user) throws Exception {
//        System.out.println(user);
        user.setUserId(String.valueOf(System.currentTimeMillis()));
        user.setUserPassword(MD5.getEncryptMD5Str(user.getUserPassword()));
        userService.userRegister(user);
        return true;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return 成功返回true，失败返回false
     */
    @GetMapping("/userLogin")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    public boolean userLogin(String username,
                             String password,
                             HttpServletRequest request) throws Exception {
        password = MD5.getEncryptMD5Str(password);
        User user = userService.getUserByUsernameAndPassword(username, password);
//        System.out.println(request.getRemoteAddr());
        if (user != null) {
            request.getSession().setAttribute("UserInformation", user);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 用户退出
     * @return 返回true
     */
    @GetMapping("/userLogout")
    @ApiOperation("用户退出")
    public boolean userLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return true;
    }


    /**
     * 异步校验旧密码是否正确
     * @param request
     * @param password
     * @return
     * @throws Exception
     */
    @GetMapping("/checkPassword")
    @ApiOperation("异步校验旧密码是否正确")
    @ApiImplicitParam(name = "password",value = "旧密码")
    public boolean checkPassword(HttpServletRequest request, String password) throws Exception {
        password = MD5.getEncryptMD5Str(password);
        User user = (User) request.getSession().getAttribute("UserInformation");
        assert password != null;
        return password.equals(user.getUserPassword());
    }


    /**
     * 修改密码
     * @param request
     * @param password
     * @return
     */
    @GetMapping("/resetPassword")
    @ApiOperation("修改密码")
    @ApiImplicitParam(name = "password",value = "新密码")
    public boolean resetPassword(HttpServletRequest request,String password){
        User user = (User) request.getSession().getAttribute("UserInformation");
        password = MD5.getEncryptMD5Str(password);
        userService.resetPassword(user.getUserId(),password);
        request.getSession().invalidate();
        return true;
    }


}
