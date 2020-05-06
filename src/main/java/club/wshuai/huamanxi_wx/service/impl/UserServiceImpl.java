package club.wshuai.huamanxi_wx.service.impl;

import club.wshuai.huamanxi_wx.dao.UserDao;
import club.wshuai.huamanxi_wx.domain.User;
import club.wshuai.huamanxi_wx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 查看用户名是否存在
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public boolean isExist(String username) throws Exception {
        User byUsername = userDao.findByUsername(username);
        //数据库中不存在该用户名故可以使用
        //数据库中存在该用户名不能使用
        return byUsername == null;
    }

    /**
     * 注册用户
     * @param user
     * @throws Exception
     */
    @Override
    public void userRegister(User user) throws Exception {
        userDao.userRegister(user);
    }

    @Override
    public void setUserAndTag(String userId, String[] tag) {
        if (tag!=null&&tag.length!=0){
            for (String s : tag) {
                userDao.setUserAndTag(userId,Integer.valueOf(s));
            }
        }
    }

    @Override
    public void setUserAndAuthor(String userId, String[] author) {
        if (author!=null&&author.length!=0){
            for (String s : author) {
                userDao.setUserAndAuthor(userId,Integer.valueOf(s));
            }
        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws Exception {
        return userDao.getUserByUsernameAndPassword(username,password);
    }

    @Override
    public void resetPassword(String userId, String password2) {
        userDao.resetPassword(userId,password2);
    }
}
