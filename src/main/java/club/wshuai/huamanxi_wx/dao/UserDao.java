package club.wshuai.huamanxi_wx.dao;

import club.wshuai.huamanxi_wx.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    @Select("select * from user where username=#{username}")
    User findByUsername(String username)throws Exception;

    @Insert("INSERT INTO user (userId, username, userPassword, userEmail, userSex) VALUES(#{userId},#{username},#{userPassword},#{userEmail},#{userSex})")
    void userRegister(User user)throws Exception;

    @Insert("INSERT INTO userandtag (userId, tagId) VALUES(#{userId},#{tagId})")
    void setUserAndTag(@Param("userId") String userId, @Param("tagId") Integer tagId);

    @Insert("INSERT INTO userandauthor (userId, authorId) VALUES(#{userId},#{authorId})")
    void setUserAndAuthor(@Param("userId") String userId, @Param("authorId") Integer authorId);

    @Select("select * from user where username=#{username} and userPassword = #{password}")
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE user SET userPassword = #{password2} WHERE userId = #{userId}")
    void resetPassword(@Param("userId") String userId, @Param("password2") String password2);
}
