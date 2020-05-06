package club.wshuai.huamanxi_wx.esdao;

import club.wshuai.huamanxi_wx.domain.Author;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AuthorDao extends ElasticsearchRepository<Author,Integer> {

    /**
     * 根据姓名查询诗人
     * @param name
     * @return
     */
    public List<Author> findByAuthorName(String name)throws Exception;

    /**
     * 根据诗人ID查询诗人
     * @param authorId
     * @return
     * @throws Exception
     */
    public Author findByAuthorId(Integer authorId)throws Exception;

}
