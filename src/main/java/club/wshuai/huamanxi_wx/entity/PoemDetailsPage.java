package club.wshuai.huamanxi_wx.entity;

import club.wshuai.huamanxi_wx.domain.Poem;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PoemDetailsPage implements Serializable {

    private Poem poem;
    private List<PoetryPagePoem> similarPoems;
    private boolean collected;

}
