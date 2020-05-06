package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AuthorPageAuthor implements Serializable {

    private Integer authorId;
    private String authorName;
    private String authorDesc;
    private String authorImg;

}
