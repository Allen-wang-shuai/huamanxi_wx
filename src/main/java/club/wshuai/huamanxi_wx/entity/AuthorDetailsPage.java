package club.wshuai.huamanxi_wx.entity;

import club.wshuai.huamanxi_wx.domain.Author;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class AuthorDetailsPage implements Serializable {

    private Author author;
    private List<PoetryPagePoem> thisAuthorPoems;
    private boolean collected;

}
