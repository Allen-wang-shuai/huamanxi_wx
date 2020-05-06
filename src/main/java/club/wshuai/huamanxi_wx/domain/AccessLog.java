package club.wshuai.huamanxi_wx.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessLog implements Serializable {

    private Long visitTime;
    private String userId;
    private String username;
    private String accessIP;
    private String url;
    private String parameter;
    private  Long executionTime;
    private String method;

    @Override
    public String toString() {
        return visitTime + "," + userId + "," + username + "," + accessIP + "," + url + "," + parameter + "," + executionTime + "," + method;
    }
}
