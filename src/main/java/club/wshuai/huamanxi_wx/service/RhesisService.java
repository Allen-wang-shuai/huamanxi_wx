package club.wshuai.huamanxi_wx.service;

import club.wshuai.huamanxi_wx.domain.Rhesis;
import club.wshuai.huamanxi_wx.entity.RhesisPageRhesis;

import java.util.List;

public interface RhesisService {
    List<RhesisPageRhesis> findRhesisPageRhesis(Integer elementNum)throws Exception;

    List<Rhesis> queryRhesis(String queryText)throws Exception;
}
