package club.wshuai.huamanxi_wx.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * shards：指定该索引库的分片数目，默认是5
 * replicas：指定每个分片的复制数目，默认是1
 */
@Document(indexName = "author",type = "author")
@Data
@ToString
public class Author implements Serializable {

    @Id
    @Field(type = FieldType.Keyword,store = true)
    private Integer authorId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorInitials;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorName;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorDynasty;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorDesc;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorIntro;

    @Field(type = FieldType.Integer,store = true)
    private Integer authorStar;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorImg;

}
