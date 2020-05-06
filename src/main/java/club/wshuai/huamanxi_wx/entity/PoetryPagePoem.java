package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PoetryPagePoem implements Serializable {

    private Integer poemId;
    private String poemTitle;
    private String poemDynasty;
    private String poemAuthor;
    private String poemAuthorId;
    private String poemContent;

}
