package club.wshuai.huamanxi_wx.entity;

import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.domain.Rhesis;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class ComprehensiveQuery implements Serializable {

    private List<PoemTest> poems;
    private List<Author> authors;
    private List<Rhesis> rheses;
    private String queryText;

}
