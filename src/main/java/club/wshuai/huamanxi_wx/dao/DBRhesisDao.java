package club.wshuai.huamanxi_wx.dao;

import club.wshuai.huamanxi_wx.entity.RhesisPageRhesis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DBRhesisDao {

    @Select("SELECT poemId, poemTitle, authorName, rhesisContent FROM rhesis LIMIT #{elementNum},20")
    List<RhesisPageRhesis> findRhesisPageRhesis(Integer elementNum)throws Exception;
}
