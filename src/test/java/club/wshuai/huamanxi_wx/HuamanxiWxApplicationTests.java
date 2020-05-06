package club.wshuai.huamanxi_wx;

import club.wshuai.huamanxi_wx.dao.DBPoemDao;
import club.wshuai.huamanxi_wx.entity.PoetryPagePoem;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HuamanxiWxApplication.class})
class HuamanxiWxApplicationTests {

    @Autowired
    private DBPoemDao dbPoemDao;

//    @Test
//    public void testPoetryPagePoem() throws Exception {
//        List<PoetryPagePoem> poetryPagePoems = dbPoemDao.findPoetryPagePoem(0);
//        System.out.println(poetryPagePoems.get(0));
//        System.out.println(poetryPagePoems.get(1));
//        System.out.println(poetryPagePoems.get(2));
//    }

}
