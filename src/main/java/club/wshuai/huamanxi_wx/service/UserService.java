package club.wshuai.huamanxi_wx.service;


import club.wshuai.huamanxi_wx.domain.User;

public interface UserService {

    boolean isExist(String username)throws Exception;

    void userRegister(User user)throws Exception;

    void setUserAndTag(String userId, String[] tag);

    void setUserAndAuthor(String userId, String[] author);

    User getUserByUsernameAndPassword(String username, String password)throws Exception;

    void resetPassword(String userId, String password2);
}
