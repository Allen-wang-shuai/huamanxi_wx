package club.wshuai.huamanxi_wx.esdao;

import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.entity.PoemTest;
import club.wshuai.huamanxi_wx.entity.PoetryPagePoem;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PoemDao extends ElasticsearchRepository<Poem,Integer> {

    /**
     * 根据诗词id查询
     * @param poemId
     * @return
     * @throws Exception
     */
    Poem findByPoemId(Integer poemId)throws Exception;

    /**
     * 根据诗词标题或者作者或者内容查询
     * @param title
     * @param author
     * @param content
     * @param pageable
     * @return
     * @throws Exception
     */
    List<Poem> findByPoemTitleOrPoemAuthorOrPoemContent(String title, String author, String content, Pageable pageable)throws Exception;

    /**
     * 根据诗词题目查询古诗
     * @param title
     * @return
     */
    List<Poem> findByPoemTitle(String title);

    List<PoemTest> findPoemsByPoemAuthor(String authorName, Pageable pageable);

}
