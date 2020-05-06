package club.wshuai.huamanxi_wx.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class User implements Serializable {

    private String userId;
    private String username;
    private String userPassword;
    private String userEmail;
    private Integer userSex;

}
