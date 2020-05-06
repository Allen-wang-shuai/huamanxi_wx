package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PoemQueryByDynasty implements Serializable {

    private List<PoetryPagePoem> poems;
    private String dynastyName;
    private Integer poemElementNum;

}
