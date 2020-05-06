package club.wshuai.huamanxi_wx.service.impl;

import club.wshuai.huamanxi_wx.dao.DBAuthorDao;
import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.entity.AuthorPageAuthor;
import club.wshuai.huamanxi_wx.esdao.AuthorDao;
import club.wshuai.huamanxi_wx.esdao.PoemDao;
import club.wshuai.huamanxi_wx.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private DBAuthorDao dbAuthorDao;

    @Override
    public Author findAuthorById(int authorId) throws Exception {
        return authorDao.findByAuthorId(authorId);
    }

    @Override
    public List<AuthorPageAuthor> findAuthorPageAuthor(Integer elementNum)throws Exception {
        return dbAuthorDao.findAuthorPageAuthor(elementNum);
    }

    @Override
    public List<AuthorPageAuthor> findAuthorPageAuthorByUserId(String userId, Integer authorElementNum) throws Exception {
        return dbAuthorDao.findAuthorPageAuthorByUserId(userId,authorElementNum);
    }

    @Override
    public boolean findIfCollected(String userId, Integer authorId) throws Exception {
        String theUserId = dbAuthorDao.findUserIdByAuthorId(userId,authorId);
        return theUserId != null && theUserId.equals(userId);
    }

    @Override
    public List<Author> queryAuthor(String queryText) throws Exception {
        return authorDao.findByAuthorName(queryText);
    }

    @Override
    public void collectAuthor(String userId, Integer authorId) throws Exception {
        dbAuthorDao.collectAuthor(userId, authorId);
    }

    @Override
    public void cancelCollectAuthor(String userId, Integer authorId) throws Exception {
        dbAuthorDao.cancelCollectAuthor(userId, authorId);
    }

    @Override
    public void addAuthorStarByAuthorId(Integer authorId) {
        dbAuthorDao.addAuthorStarByAuthorId(authorId);
    }

    @Override
    public void reduceAuthorStarByAuthorId(Integer authorId) {
        dbAuthorDao.reduceAuthorStarByAuthorId(authorId);
    }
}
