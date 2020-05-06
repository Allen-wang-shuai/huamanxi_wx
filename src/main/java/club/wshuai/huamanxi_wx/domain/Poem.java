package club.wshuai.huamanxi_wx.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "poem",type = "poem")
@Data
@ToString
public class Poem implements Serializable {

    @Id
    @Field(type = FieldType.Keyword,store = true)
    private Integer poemId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemTitle;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemDynasty;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemAuthor;

    @Field(type = FieldType.Integer,store = true)
    private Integer poemAuthorId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemContent;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemTagNames;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemTranslation;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemAppreciation;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemBackground;

    @Field(type = FieldType.Integer,store = true)
    private Integer poemStar;

}


