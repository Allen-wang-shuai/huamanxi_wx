package club.wshuai.huamanxi_wx.service.impl;

import club.wshuai.huamanxi_wx.dao.AOPDao;
import club.wshuai.huamanxi_wx.domain.AccessLog;
import club.wshuai.huamanxi_wx.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AopServiceImpl implements AopService {

    @Autowired
    private AOPDao aopDao;

    @Override
    public void save(AccessLog accessLog) {
        aopDao.save(accessLog);
    }
}
