package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PoemQueryByAuthor implements Serializable {

    private List<PoetryPagePoem> poems;
    private String authorName;
    private Integer poemElementNum;

}
