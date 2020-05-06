package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RhesisPageRhesis implements Serializable {

    private Integer poemId;
    private String poemTitle;
    private String authorName;
    private String rhesisContent;

}
