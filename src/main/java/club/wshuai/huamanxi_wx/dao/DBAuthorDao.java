package club.wshuai.huamanxi_wx.dao;

import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.entity.AuthorPageAuthor;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DBAuthorDao {

    @Select("SELECT * FROM author WHERE authorId = #{authorId}")
    public Author findAuthorByAuthorId(Integer authorId)throws Exception;

    @Select("SELECT authorId, authorName, authorDesc, authorImg FROM author ORDER BY authorStar DESC LIMIT #{elementNum},5")
    List<AuthorPageAuthor> findAuthorPageAuthor(Integer elementNum)throws Exception;

    @Select("select authorId, authorName, authorDesc, authorImg from author where authorId in (select authorId from userandauthor where userId=#{userId}) LIMIT #{authorElementNum},5")
    List<AuthorPageAuthor> findAuthorPageAuthorByUserId(@Param("userId") String userId, @Param("authorElementNum") Integer authorElementNum)throws Exception;

    @Select("SELECT userId FROM userandauthor WHERE authorId = #{authorId} AND userId = #{userId}")
    String findUserIdByAuthorId(@Param("userId") String userId, @Param("authorId") Integer authorId)throws Exception;

    @Insert("INSERT INTO userandauthor (userId, authorId) VALUES(#{userId}, #{authorId})")
    void collectAuthor(@Param("userId") String userId, @Param("authorId") Integer authorId);

    @Delete("DELETE FROM userandauthor WHERE userId = #{userId} AND authorId = #{authorId}")
    void cancelCollectAuthor(@Param("userId") String userId, @Param("authorId") Integer authorId);

    @Update("UPDATE author SET authorStar=authorStar+1 WHERE authorId = #{authorId}")
    void addAuthorStarByAuthorId(Integer authorId);

    @Update("UPDATE author SET authorStar=authorStar-1 WHERE authorId = #{authorId}")
    void reduceAuthorStarByAuthorId(Integer authorId);
}
