package club.wshuai.huamanxi_wx.dao;

import club.wshuai.huamanxi_wx.domain.AccessLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AOPDao {

    @Insert("INSERT INTO accesslog (visitTime, userId, username, accessIP, url, parameter, executionTime, method)" +
            " VALUES (#{visitTime}, #{userId}, #{username}, #{accessIP}, #{url}, #{parameter}, #{executionTime}, #{method})")
    void save(AccessLog accessLog);

}
