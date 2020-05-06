package club.wshuai.huamanxi_wx.configuration;

import club.wshuai.huamanxi_wx.interceptor.CentreInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class MvcConfiguration implements WebMvcConfigurer {

    //设置全局静态变量，项目一启动就存在
    public static Integer index = 0;

    /**
     * 设置定时任务每天零点执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    private void configureTasks() {
        if (index <= 366) {
            index += 2;
        } else {
            index = 0;
        }
    }

    /**
     * 向容器中加入我们自己的WebMvcConfigurer一定要加@Bean注解！！！
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            //注册拦截器，这里你详细指定了拦截路径，并没有拦截静态资源，所以不用注意释放静态资源
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new CentreInterceptor())
                        .addPathPatterns("/pageController/**")
                        .excludePathPatterns("/pageController/loadHomePage","/pageController/toPoetryPage","/pageController/toAuthorPage","/pageController/toRhesisPage","/pageController/guessYouLike","/pageController/likePoem");
            }

            //注册前端控制器，访问该项目时请求/pageController/loadHomePage这个方法
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("forward:/pageController/loadHomePage");
            }
        };

        return webMvcConfigurer;
    }

}
