package club.wshuai.huamanxi_wx.service.impl;

import club.wshuai.huamanxi_wx.dao.DBPoemDao;
import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.entity.PoemTest;
import club.wshuai.huamanxi_wx.entity.PoetryPagePoem;
import club.wshuai.huamanxi_wx.esdao.AuthorDao;
import club.wshuai.huamanxi_wx.esdao.PoemDao;
import club.wshuai.huamanxi_wx.service.PoemService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PoemServiceImpl implements PoemService {

    @Autowired
    private PoemDao poemDao;
    @Autowired
    private DBPoemDao dbPoemDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Poem findPoemById(Integer poemId) throws Exception {
        return poemDao.findByPoemId(poemId);
    }

    @Override
    public List<Poem> findBestPoem(Integer index) throws Exception{
        return dbPoemDao.findBestPoem(index);
    }

    @Override
    public List<Poem> findTwoPoems(String[] poemIds) throws Exception{
        return dbPoemDao.findTwoPoems(Integer.valueOf(poemIds[0]),Integer.valueOf(poemIds[1]));
    }

    @Override
    public List<PoetryPagePoem> findPoetryPagePoem(Integer elementNum) throws Exception{
        return dbPoemDao.findPoetryPagePoem(elementNum);
    }

    @Override
    public List<PoetryPagePoem> findPoetryPagePoemByUserId(String userId,Integer poemElementNum) throws Exception {
        return dbPoemDao.findPoetryPagePoemByUserId(userId,poemElementNum);
    }

    @Override
    public List<PoetryPagePoem> findSimilarPoems(String[] poemIds) throws Exception {
        return dbPoemDao.findSimilarPoems(Integer.valueOf(poemIds[0]),Integer.valueOf(poemIds[1]),Integer.valueOf(poemIds[2]),Integer.valueOf(poemIds[3]),Integer.valueOf(poemIds[4]));
    }

    @Override
    public boolean findIfCollected(String userId, Integer poemId) throws Exception {
        String theUserId = dbPoemDao.findUserIdByPoemId(userId,poemId);
        return theUserId != null && theUserId.equals(userId);
    }

    @Override
    public List<PoetryPagePoem> findPoemByAuthorId(Integer authorId) throws Exception {
        return dbPoemDao.findPoemByAuthorId(authorId);
    }


    //不需要高亮之后的查询
    @Override
    public List<PoemTest> queryPoems(String queryText) throws Exception {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                //可通过field()设置搜索域，多个表示指定在这几个域中进行搜索，如果不设置搜索域则默认在所有的域中进行搜索
                .withQuery(QueryBuilders.multiMatchQuery(queryText,"poemTitle","poemContent","poemAuthor").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).operator(Operator.OR).minimumShouldMatch("50%"))
                .withPageable(PageRequest.of(0,10))
                .build();

        //执行查询
        return elasticsearchTemplate.queryForList(query, PoemTest.class);
    }

    @Override
    public List<PoetryPagePoem> poemQueryByTag(Integer poemElementNum, String tagName) throws Exception {
        return dbPoemDao.poemQueryByTag(poemElementNum,tagName);
    }

    @Override
    public List<PoetryPagePoem> poemQueryByAuthor(Integer poemElementNum, String authorName) throws Exception {
//        SortBuilder sortBuilder = SortBuilders.fieldSort("poemStar").order(SortOrder.DESC);
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.termQuery("poemAuthor",authorName))
//                .withPageable(PageRequest.of(authorElementNum,5))
//                .withSort(sortBuilder).build();
//
//        return elasticsearchTemplate.queryForList(query,PoemTest.class);
        return dbPoemDao.poemQueryByAuthor(poemElementNum,authorName);
    }

    @Override
    public List<PoetryPagePoem> findGuessYouLikePoems(String[] poemIds) throws Exception {
        return dbPoemDao.findGuessYouLikePoems(Integer.valueOf(poemIds[0]),Integer.valueOf(poemIds[1]),Integer.valueOf(poemIds[2]),Integer.valueOf(poemIds[3]),Integer.valueOf(poemIds[4]),Integer.valueOf(poemIds[5]));
    }

    @Override
    public List<PoetryPagePoem> findTheBestSixPoem() throws Exception {
        return dbPoemDao.findTheBestSixPoem();
    }

    @Override
    public void collectPoem(String userId, Integer poemId) throws Exception {
        dbPoemDao.collectPoem(userId, poemId);
    }

    @Override
    public void cancelCollectPoem(String userId, Integer poemId) throws Exception {
        dbPoemDao.cancelCollectPoem(userId, poemId);
    }

    @Override
    public void addPoemStarByPoemId(Integer poemId) throws Exception {
        dbPoemDao.addPoemStarByPoemId(poemId);
    }

    @Override
    public void reducePoemStarByPoemId(Integer poemId) throws Exception {
        dbPoemDao.reducePoemStarByPoemId(poemId);
    }

    @Override
    public List<PoetryPagePoem> poemQueryByDynasty(Integer poemElementNum, String dynastyName) throws Exception {
        return dbPoemDao.poemQueryByDynasty(poemElementNum,dynastyName);
    }

    @Override
    public Poem findPoemTestById(String poemId) throws Exception {
        return poemDao.findByPoemId(Integer.valueOf(poemId));
    }

}
