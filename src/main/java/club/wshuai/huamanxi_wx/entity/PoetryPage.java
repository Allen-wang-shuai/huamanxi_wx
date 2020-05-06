package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PoetryPage implements Serializable {

    private List<PoetryPagePoem> poetryPagePoems;
    private Integer poemElementNum;
    private boolean ifPaging;

}
