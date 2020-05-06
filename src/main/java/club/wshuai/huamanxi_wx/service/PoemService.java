package club.wshuai.huamanxi_wx.service;

import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.entity.PoemTest;
import club.wshuai.huamanxi_wx.entity.PoetryPagePoem;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;

public interface PoemService {
    Poem findPoemById(Integer poemId) throws Exception;

    List<Poem> findBestPoem(Integer index)throws Exception;

    List<Poem> findTwoPoems(String[] poemIds)throws Exception;

    List<PoetryPagePoem> findPoetryPagePoem(Integer elementNum)throws Exception;

    List<PoetryPagePoem> findPoetryPagePoemByUserId(String userId,Integer poemElementNum)throws Exception;

    List<PoetryPagePoem> findSimilarPoems(String[] poemIds)throws Exception;

    boolean findIfCollected(String userId, Integer poemId)throws Exception;

    List<PoetryPagePoem> findPoemByAuthorId(Integer authorId)throws Exception;

    List<PoemTest> queryPoems(String queryText)throws Exception;

    List<PoetryPagePoem> poemQueryByTag(Integer poemElementNum, String tagName)throws Exception;

    List<PoetryPagePoem> poemQueryByAuthor(Integer poemElementNum, String authorName)throws Exception;

    List<PoetryPagePoem> findGuessYouLikePoems(String[] poemIds)throws Exception;

    List<PoetryPagePoem> findTheBestSixPoem()throws Exception;

    void collectPoem(String userId, Integer poemId)throws Exception;

    void cancelCollectPoem(String userId, Integer poemId)throws Exception;

    void addPoemStarByPoemId(Integer poemId)throws Exception;

    void reducePoemStarByPoemId(Integer poemId)throws Exception;

    List<PoetryPagePoem> poemQueryByDynasty(Integer poemElementNum, String dynastyName)throws Exception;

    Poem findPoemTestById(String poemId) throws Exception;
}
