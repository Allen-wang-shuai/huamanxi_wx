package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@ToString
@Document(indexName = "poem",type = "poem")
public class PoemTest implements Serializable {

    @Id
    @Field(type = FieldType.Keyword,store = true)
    private Integer poemId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemTitle;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemDynasty;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemAuthor;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemContent;

}


