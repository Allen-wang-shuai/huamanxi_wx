package club.wshuai.huamanxi_wx.service.impl;

import club.wshuai.huamanxi_wx.dao.DBRhesisDao;
import club.wshuai.huamanxi_wx.domain.Rhesis;
import club.wshuai.huamanxi_wx.entity.RhesisPageRhesis;
import club.wshuai.huamanxi_wx.esdao.RhesisDao;
import club.wshuai.huamanxi_wx.service.RhesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RhesisServiceImpl implements RhesisService {

    @Autowired
    private RhesisDao rhesisDao;
    @Autowired
    private DBRhesisDao dbRhesisDao;

    @Override
    public List<RhesisPageRhesis> findRhesisPageRhesis(Integer elementNum) throws Exception {
        return dbRhesisDao.findRhesisPageRhesis(elementNum);
    }

    @Override
    public List<Rhesis> queryRhesis(String queryText) throws Exception {
        return rhesisDao.findByRhesisContentOrAuthorName(queryText,queryText, PageRequest.of(0,10));
    }
}
