package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class RhesisPage implements Serializable {

    private List<RhesisPageRhesis> rhesisPageRheses;
    private Integer rhesisElementNum;

}
