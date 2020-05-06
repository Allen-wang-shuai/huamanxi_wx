package club.wshuai.huamanxi_wx.controller;

import club.wshuai.huamanxi_wx.entity.*;
import club.wshuai.huamanxi_wx.service.AuthorService;
import club.wshuai.huamanxi_wx.service.PoemService;
import club.wshuai.huamanxi_wx.service.RhesisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queryController")
@Api(value = "查询接口",description = "提供综合查询以及按类别查询等方法")
public class QueryController {

    @Autowired
    private PoemService poemService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private RhesisService rhesisService;


    /**
     * 综合查询
     * @param queryText
     * @return
     * @throws Exception
     */
    @GetMapping("/comprehensiveQuery")
    @ApiOperation("综合查询")
    @ApiImplicitParam(name = "queryText",value = "用户输入的搜索文本内容",required = true)
    public ComprehensiveQuery comprehensiveQuery(String queryText)throws Exception{
        ComprehensiveQuery comprehensiveQuery = new ComprehensiveQuery();
        comprehensiveQuery.setQueryText(queryText);
        comprehensiveQuery.setPoems(poemService.queryPoems(queryText));
        comprehensiveQuery.setAuthors(authorService.queryAuthor(queryText));
        comprehensiveQuery.setRheses(rhesisService.queryRhesis(queryText));

        return comprehensiveQuery;
    }

    /**
     * 分类查询诗词之按标签查询
     * @param poemElementNum
     * @param tagName
     * @return
     * @throws Exception
     */
    @GetMapping("/poemQueryByTag")
    @ApiOperation("分类查询诗词之按标签查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poemElementNum",value = "翻页所需要的诗词元素数，从0开始，每次翻页数字加5"),
            @ApiImplicitParam(name = "tagName",value = "标签名")
    })
    public PoemQueryByTag poemQueryByTag(@RequestParam(name = "poemElementNum", required = false, defaultValue = "0") Integer poemElementNum,
                                         @RequestParam(name = "tagName", required = false, defaultValue = "写景") String tagName)throws Exception{
        PoemQueryByTag poemQueryByTag = new PoemQueryByTag();
        poemQueryByTag.setPoemElementNum(poemElementNum);
        poemQueryByTag.setTagName(tagName);
        poemQueryByTag.setPoems(poemService.poemQueryByTag(poemElementNum,tagName));
        return poemQueryByTag;
    }


    /**
     * 分类查询诗词之按作者查询
     * @param poemElementNum
     * @param authorName
     * @return
     * @throws Exception
     */
    @GetMapping("/poemQueryByAuthor")
    @ApiOperation("分类查询诗词之按作者查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poemElementNum",value = "翻页所需要的诗词元素数，从0开始，每次翻页数字加5"),
            @ApiImplicitParam(name = "authorName",value = "诗人名字")
    })
    public PoemQueryByAuthor poemQueryByAuthor(@RequestParam(name = "poemElementNum", required = false, defaultValue = "0") Integer poemElementNum,
                                               @RequestParam(name = "authorName", required = false, defaultValue = "李白") String authorName)throws Exception{
        PoemQueryByAuthor poemQueryByAuthor = new PoemQueryByAuthor();
        poemQueryByAuthor.setAuthorName(authorName);
        poemQueryByAuthor.setPoemElementNum(poemElementNum);
        poemQueryByAuthor.setPoems(poemService.poemQueryByAuthor(poemElementNum,authorName));
        return poemQueryByAuthor;
    }

    /**
     * 分类查询诗词之按朝代查询
     * @param poemElementNum
     * @param dynastyName
     * @return
     * @throws Exception
     */
    @GetMapping("/poemQueryByDynasty")
    @ApiOperation("分类查询诗词之按朝代查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "poemElementNum",value = "翻页所需要的诗词元素数，从0开始，每次翻页数字加5"),
            @ApiImplicitParam(name = "dynastyName",value = "朝代名字")
    })
    public PoemQueryByDynasty poemQueryByDynasty(@RequestParam(name = "poemElementNum", required = false, defaultValue = "0") Integer poemElementNum,
                                                @RequestParam(name = "dynastyName", required = false, defaultValue = "唐代") String dynastyName)throws Exception{
        PoemQueryByDynasty poemQueryByDynasty = new PoemQueryByDynasty();
        poemQueryByDynasty.setDynastyName(dynastyName);
        poemQueryByDynasty.setPoemElementNum(poemElementNum);
        poemQueryByDynasty.setPoems(poemService.poemQueryByDynasty(poemElementNum,dynastyName));
        return poemQueryByDynasty;
    }

}
