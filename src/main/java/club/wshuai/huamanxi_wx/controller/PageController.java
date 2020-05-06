package club.wshuai.huamanxi_wx.controller;

import club.wshuai.huamanxi_wx.configuration.MvcConfiguration;
import club.wshuai.huamanxi_wx.domain.Author;
import club.wshuai.huamanxi_wx.domain.Poem;
import club.wshuai.huamanxi_wx.domain.User;
import club.wshuai.huamanxi_wx.entity.*;
import club.wshuai.huamanxi_wx.service.AuthorService;
import club.wshuai.huamanxi_wx.service.PoemService;
import club.wshuai.huamanxi_wx.service.RhesisService;
import club.wshuai.huamanxi_wx.utils.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王帅帅
 * @version 1.0.0
 */
@RestController
@RequestMapping("/pageController")
@Api(value = "页面内容相关接口",description = "有诗词、诗人、名句等各个页面所需要的数据")
public class PageController {

    @Autowired
    private PoemService poemService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private RhesisService rhesisService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 主页请求方法
     *
     * @return 返回的是诗词的所有内容：
     * @throws Exception 抛出的是总异常
     */
    @GetMapping("/loadHomePage")
    @ApiOperation("主页请求方法，返回2首诗词")
    public List<Poem> loadHomePage() throws Exception {
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");

        //这是用户登录时首页显示的诗词
        if (user != null) {
            String userId = user.getUserId();
            //日荐的诗词，这个每天就一组
            String str = HttpRequest.sendGet("http://120.26.89.198:8000/rijian/?userId=", userId);
            str = str.substring(12, str.length() - 2);
            String[] poemIds;
            if (!str.equals("ul")) {
                poemIds = str.split(", ");
            } else {
                return poemService.findBestPoem(MvcConfiguration.index);
            }
            return poemService.findTwoPoems(poemIds);
        } else {
            //这是用户未登录时首页显示的诗词，当noLoginPoems为null时才执行查询减少资源消耗
            return poemService.findBestPoem(MvcConfiguration.index);
        }
    }

    /**
     * 诗词页面需要的数据
     *
     * @param poemElementNum 诗词元素数，从0开始，前台点击下一页每次传递过来的参数加5，实现翻页
     * @return
     */
    @GetMapping("/toPoetryPage")
    @ApiOperation("诗词页面需要的数据")
    @ApiImplicitParam(name = "poemElementNum",value = "翻页所需要的诗词元素数，从0开始，每次翻页数字加5")
    public PoetryPage toPoetryPage(@RequestParam(name = "poemElementNum", required = false, defaultValue = "0") Integer poemElementNum) throws Exception {
        PoetryPage poetryPage = new PoetryPage();
        poetryPage.setPoetryPagePoems(poemService.findPoetryPagePoem(poemElementNum));
        poetryPage.setPoemElementNum(poemElementNum);
        return poetryPage;
    }

    /**
     * 诗人页面所需要的数据
     *
     * @param authorElementNum 诗人元素数，从0开始，前台点击下一页每次传递过来的参数加5，实现翻页
     * @return
     */
    @GetMapping("/toAuthorPage")
    @ApiOperation("诗人页面需要的数据")
    @ApiImplicitParam(name = "authorElementNum",value = "翻页所需要的诗人元素数，从0开始，每次翻页数字加5")
    public AuthorPage toAuthorPage(@RequestParam(name = "authorElementNum", required = false, defaultValue = "0") Integer authorElementNum) throws Exception {
        AuthorPage authorPage = new AuthorPage();
        authorPage.setAuthorPageAuthors(authorService.findAuthorPageAuthor(authorElementNum));
        authorPage.setAuthorElementNum(authorElementNum);
        return authorPage;
    }

    /**
     * 名句页面所需要的数据
     *
     * @param rhesisElementNum 名句元素数，从0开始，前台点击下一页每次传递过来的参数加20，实现翻页
     * @return
     * @throws Exception
     */
    @GetMapping("/toRhesisPage")
    @ApiOperation("名句页面需要的数据")
    @ApiImplicitParam(name = "rhesisElementNum",value = "翻页所需要的名句元素数，从0开始，每次翻页数字加5")
    public RhesisPage toRhesisPage(@RequestParam(name = "rhesisElementNum", required = false, defaultValue = "0") Integer rhesisElementNum) throws Exception {
        RhesisPage rhesisPage = new RhesisPage();
        rhesisPage.setRhesisPageRheses(rhesisService.findRhesisPageRhesis(rhesisElementNum));
        rhesisPage.setRhesisElementNum(rhesisElementNum);
        return rhesisPage;
    }

    /**
     * 猜你喜欢需要的诗词
     *
     * @return
     */
    @GetMapping("/guessYouLike")
    @ApiOperation("猜你喜欢需要的诗词，返回5首诗词登录前后内容不同")
    public List<PoetryPagePoem> guessYouLike() throws Exception {
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");

        //下面是用户登录后才会呈现的内容
        if (user != null) {
            String userId = user.getUserId();
            //十个猜你喜欢的诗词，这个是实时推荐
            String str = HttpRequest.sendGet("http://120.26.89.198:8000/online_server?userId=", userId);
            str = str.substring(8, str.length() - 2);
            String[] poemIds = str.split(", ");
            //这个查询要比下面的快，多id查询最好用数据库的IN命令很快的，不要一条一条的查
            return poemService.findGuessYouLikePoems(poemIds);
        } else {
            return poemService.findTheBestSixPoem();
        }
    }

    /**
     * 为诗词点赞
     *
     * @param poemId
     * @param like
     * @return
     * @throws Exception
     */
    @GetMapping("/likePoem")
    @ApiOperation("为诗词点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poemId",value = "诗词Id"),
            @ApiImplicitParam(name = "like",value = "标记，0是点赞，1是取消点赞")
    })
    public boolean likePoem(Integer poemId, Integer like) throws Exception {
        if (like == 0) {
            poemService.addPoemStarByPoemId(poemId);
        } else if (like == 1) {
            poemService.reducePoemStarByPoemId(poemId);
        }
        return true;
    }


    /////////////////////////////////////////////////以下内容需登录后才能查看/////////////////////////////////////////////

    /**
     * 判断用户是否登录
     *
     * @return
     */
    @GetMapping("/ifLogin")
    @ApiOperation("判断用户是否登录")
    public boolean ifLogin() {
        User UserInformation = (User) request.getSession().getAttribute("UserInformation");
        return UserInformation != null;
    }

    /**
     * 个人中心之诗词收藏页面所需要的数据
     *
     * @param poemElementNum 诗词元素数，从0开始，前台点击下一页每次传递过来的参数加5，实现翻页
     * @return
     * @throws Exception
     */
    @GetMapping("/toPoemCollectedPage")
    @ApiOperation("个人中心之诗词收藏页面所需要的数据")
    @ApiImplicitParam(name = "poemElementNum",value = "翻页所需要的诗词元素数，从0开始，每次翻页数字加5")
    public PoetryPage toPoemCollectedPage(@RequestParam(name = "poemElementNum", required = false, defaultValue = "0") Integer poemElementNum) throws Exception {
        PoetryPage poetryPage = new PoetryPage();
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();
        poetryPage.setIfPaging(!(poemService.findPoetryPagePoemByUserId(userId, poemElementNum)).isEmpty());
        poetryPage.setPoetryPagePoems(poemService.findPoetryPagePoemByUserId(userId, poemElementNum));
        poetryPage.setPoemElementNum(poemElementNum);
        return poetryPage;
    }

    /**
     * 个人中心之诗人收藏页面所需要的数据
     *
     * @param authorElementNum 诗人元素数，从0开始，前台点击下一页每次传递过来的参数加5，实现翻页
     * @return
     */
    @GetMapping("/toAuthorCollectedPage")
    @ApiOperation("个人中心之诗人收藏页面所需要的数据")
    @ApiImplicitParam(name = "authorElementNum",value = "翻页所需要的诗人元素数，从0开始，每次翻页数字加5")
    public AuthorPage toAuthorCollectedPage(@RequestParam(name = "authorElementNum", required = false, defaultValue = "0") Integer authorElementNum) throws Exception {
        AuthorPage authorPage = new AuthorPage();
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();
        authorPage.setIfPaging(!(authorService.findAuthorPageAuthorByUserId(userId, authorElementNum).isEmpty()));
        authorPage.setAuthorPageAuthors(authorService.findAuthorPageAuthorByUserId(userId, authorElementNum));
        authorPage.setAuthorElementNum(authorElementNum);
        return authorPage;
    }

    /**
     * 诗词详情页跳转
     *
     * @param poemId 诗词ID
     * @return 返回的是诗词的所有内容
     * @throws Exception 抛出的是总异常
     */
    @GetMapping("/toPoemDetails")
    @ApiOperation("诗词详情页跳转")
    @ApiImplicitParam(name = "poemId",value = "诗词Id")
    public PoemDetailsPage toPoemDetails(Integer poemId) throws Exception {
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();

        PoemDetailsPage poemDetailsPage = new PoemDetailsPage();
        poemDetailsPage.setPoem(poemService.findPoemById(poemId));
        poemDetailsPage.setCollected(poemService.findIfCollected(userId, poemId));
        //相似古诗查询
        String str = HttpRequest.sendGet("http://175.24.30.126:8000?id=", String.valueOf(poemId));
        str = str.substring(1, str.length() - 1);
        String[] poemIds;
        if (!str.equals("on")) {
            poemIds = str.split(", ");
        } else {
            poemIds = new String[]{String.valueOf(poemId + 1),
                    String.valueOf(poemId + 2),
                    String.valueOf(poemId + 3),
                    String.valueOf(poemId + 4),
                    String.valueOf(poemId + 5)};
        }
        poemDetailsPage.setSimilarPoems(poemService.findSimilarPoems(poemIds));
        return poemDetailsPage;
    }

    /**
     * 作者详情页跳转
     *
     * @param authorId 诗人ID
     * @return 返回的是诗人的详情
     * @throws Exception 抛出的是总异常
     */
    @GetMapping("/toAuthorDetails")
    @ApiOperation("作者详情页跳转")
    @ApiImplicitParam(name = "authorId",value = "作者Id")
    public AuthorDetailsPage toAuthorDetails(Integer authorId) throws Exception {
        AuthorDetailsPage authorDetailsPage = new AuthorDetailsPage();
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();
        authorDetailsPage.setAuthor(authorService.findAuthorById(authorId));
        authorDetailsPage.setThisAuthorPoems(poemService.findPoemByAuthorId(authorId));
        authorDetailsPage.setCollected(authorService.findIfCollected(userId, authorId));

        return authorDetailsPage;
    }

    /**
     * 收藏诗词
     *
     * @param poemId
     * @param collection
     * @throws Exception
     */
    @GetMapping("/collectPoem")
    @ApiOperation("收藏诗词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poemId",value = "诗词Id"),
            @ApiImplicitParam(name = "collection",value = "标记，0是收藏，1是取消收藏")
    })
    public boolean collectPoem(Integer poemId, Integer collection) throws Exception {

        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();

        //0是收藏诗词
        if (collection == 0) {
            poemService.collectPoem(userId, poemId);
            poemService.addPoemStarByPoemId(poemId);
        } else if (collection == 1) {
            poemService.cancelCollectPoem(userId, poemId);
            poemService.reducePoemStarByPoemId(poemId);
        }

        return true;
    }

    /**
     * 收藏作者
     *
     * @param authorId
     * @param collection
     * @throws Exception
     */
    @GetMapping("/collectAuthor")
    @ApiOperation("收藏作者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorId",value = "作者Id"),
            @ApiImplicitParam(name = "collection",value = "标记，0是收藏，1是取消收藏")
    })
    public boolean collectAuthor(Integer authorId, Integer collection) throws Exception {
        //获得当前登录用户信息
        User user = (User) request.getSession().getAttribute("UserInformation");
        String userId = user.getUserId();

        if (collection == 0) {
            authorService.collectAuthor(userId, authorId);
            authorService.addAuthorStarByAuthorId(authorId);
        } else if (collection == 1) {
            authorService.cancelCollectAuthor(userId, authorId);
            authorService.reduceAuthorStarByAuthorId(authorId);
        }

        return true;
    }

    /**
     * 背诵页面
     *
     * @param poemId
     * @return
     */
    @GetMapping("toRecitationPage")
    @ApiOperation("背诵页面所需要的诗词")
    @ApiImplicitParam(name = "poemId",value = "诗词Id")
    public Poem toRecitationPage(String poemId) throws Exception {
        Poem poem = poemService.findPoemTestById(poemId);
        String rightPoem = poem.getPoemContent()
                .replaceAll("\\s*", "")
                .replaceAll("\\(.*?\\)", "");
        poem.setPoemContent(rightPoem);
        return poem;
    }


}
