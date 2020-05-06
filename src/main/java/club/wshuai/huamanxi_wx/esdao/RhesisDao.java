package club.wshuai.huamanxi_wx.esdao;

import club.wshuai.huamanxi_wx.domain.Rhesis;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RhesisDao extends ElasticsearchRepository<Rhesis,Integer> {

    /**
     * 通过名句内容或者作者查询
     * @param content
     * @return
     * @throws Exception
     */
    List<Rhesis> findByRhesisContentOrAuthorName(String content, String author, Pageable pageable)throws Exception;

}
