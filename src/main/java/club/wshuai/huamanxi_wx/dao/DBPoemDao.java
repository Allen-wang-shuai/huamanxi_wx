package club.wshuai.huamanxi_wx.dao;

import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.entity.PoemTest;
import club.wshuai.huamanxi_wx.entity.PoetryPagePoem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DBPoemDao {

    @Select("SELECT * FROM poem ORDER BY poemStar DESC LIMIT #{index},2")
    List<Poem> findBestPoem(Integer index)throws Exception;

    @Select("SELECT * FROM poem WHERE poemId = #{poemId}")
    Poem findByPoemId(Integer poemId)throws Exception;

    @Select("SELECT * FROM poem WHERE poemId IN(#{poemId1},#{poemId2})")
    List<Poem> findTwoPoems(@Param("poemId1") Integer poemId1, @Param("poemId2") Integer poemId2)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem ORDER BY poemStar DESC LIMIT #{elementNum},5")
    List<PoetryPagePoem> findPoetryPagePoem(@Param("elementNum") Integer elementNum)throws Exception;

    @Select("select poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent from poem where poemId in (select poemId from userandpoem where userId=#{userId}) LIMIT #{poemElementNum},5")
    List<PoetryPagePoem> findPoetryPagePoemByUserId(@Param("userId") String userId, @Param("poemElementNum")Integer poemElementNum)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE poemId IN(#{poem1},#{poem2},#{poem3},#{poem4},#{poem5})")
    List<PoetryPagePoem> findSimilarPoems(@Param("poem1") Integer poem1, @Param("poem2")Integer poem2, @Param("poem3")Integer poem3, @Param("poem4")Integer poem4, @Param("poem5")Integer poem5)throws Exception;

    @Select("SELECT userId FROM userandpoem WHERE poemId = #{poemId} AND userId = #{userId}")
    String findUserIdByPoemId(@Param("userId") String userId,@Param("poemId") Integer poemId)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE poemAuthorId = #{authorId} ORDER BY poemStar DESC LIMIT 0,5")
    List<PoetryPagePoem> findPoemByAuthorId(Integer authorId)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE LOCATE(#{tagName}, poemTagNames)>0 ORDER BY poemStar DESC LIMIT #{poemElementNum},5")
    List<PoetryPagePoem> poemQueryByTag(@Param("poemElementNum") Integer poemElementNum, @Param("tagName") String tagName)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE poemAuthor = #{authorName} ORDER BY poemStar DESC LIMIT #{poemElementNum},5")
    List<PoetryPagePoem> poemQueryByAuthor(@Param("poemElementNum") Integer poemElementNum, @Param("authorName") String authorName)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE poemId IN(#{poem1},#{poem2},#{poem3},#{poem4},#{poem5},#{poem6})")
    List<PoetryPagePoem> findGuessYouLikePoems(@Param("poem1") Integer poem1, @Param("poem2") Integer poem2, @Param("poem3") Integer poem3, @Param("poem4") Integer poem4, @Param("poem5") Integer poem5, @Param("poem6") Integer poem6);

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem ORDER BY poemStar DESC LIMIT 0,6")
    List<PoetryPagePoem> findTheBestSixPoem()throws Exception;

    @Insert("INSERT INTO userandpoem (userId, poemId) VALUES(#{userId}, #{poemId})")
    void collectPoem(@Param("userId") String userId, @Param("poemId") Integer poemId);

    @Delete("DELETE FROM userandpoem WHERE userId = #{userId} AND poemId = #{poemId}")
    void cancelCollectPoem(@Param("userId") String userId, @Param("poemId") Integer poemId);

    @Update("UPDATE poem SET poemStar=poemStar+1 WHERE poemId = #{poemId}")
    void addPoemStarByPoemId(Integer poemId)throws Exception;

    @Update("UPDATE poem SET poemStar=poemStar-1 WHERE poemId = #{poemId}")
    void reducePoemStarByPoemId(Integer poemId)throws Exception;

    @Select("SELECT poemId, poemTitle, poemDynasty, poemAuthor, poemAuthorId, poemContent FROM poem WHERE poemDynasty = #{dynastyName} ORDER BY poemStar DESC LIMIT #{poemElementNum},5")
    List<PoetryPagePoem> poemQueryByDynasty(@Param("poemElementNum") Integer poemElementNum, @Param("dynastyName") String dynastyName)throws Exception;
}
