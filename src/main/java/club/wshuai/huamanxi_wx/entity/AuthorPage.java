package club.wshuai.huamanxi_wx.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class AuthorPage implements Serializable {

    private List<AuthorPageAuthor> authorPageAuthors;
    private Integer authorElementNum;
    private boolean ifPaging;

}
