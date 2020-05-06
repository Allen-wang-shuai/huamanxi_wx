package club.wshuai.huamanxi_wx.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@ToString
@Document(indexName = "rhesis",type = "rhesis")
public class Rhesis implements Serializable {

    @Id
    @Field(type = FieldType.Integer,store = true)
    private Integer rhesisId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String rhesisContent;

    @Field(type = FieldType.Integer,store = true)
    private Integer authorId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String authorName;

    @Field(type = FieldType.Integer,store = true)
    private Integer poemId;

    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word")
    private String poemTitle;

}
