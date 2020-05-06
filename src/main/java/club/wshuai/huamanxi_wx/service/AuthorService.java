package club.wshuai.huamanxi_wx.service;

import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.entity.AuthorPageAuthor;

import java.util.List;

public interface AuthorService {
    Author findAuthorById(int authorId) throws Exception;

    List<AuthorPageAuthor> findAuthorPageAuthor(Integer elementNum)throws Exception;

    List<AuthorPageAuthor> findAuthorPageAuthorByUserId(String userId, Integer authorElementNum)throws Exception;

    boolean findIfCollected(String userId, Integer authorId)throws Exception;

    List<Author> queryAuthor(String queryText)throws Exception;

    void collectAuthor(String userId, Integer authorId)throws Exception;

    void cancelCollectAuthor(String userId, Integer authorId)throws Exception;

    void addAuthorStarByAuthorId(Integer authorId);

    void reduceAuthorStarByAuthorId(Integer authorId);
}
