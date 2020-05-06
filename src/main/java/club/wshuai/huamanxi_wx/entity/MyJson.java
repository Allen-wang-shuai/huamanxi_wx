package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MyJson implements Serializable {

    private Headers headers;
    private String body;

}
