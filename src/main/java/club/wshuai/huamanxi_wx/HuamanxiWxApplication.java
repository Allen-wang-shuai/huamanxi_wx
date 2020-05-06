package club.wshuai.huamanxi_wx;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "club.wshuai.huamanxi_wx.esdao")
@SpringBootApplication
public class HuamanxiWxApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuamanxiWxApplication.class, args);
	}

	@Value("${server.port}")
	private Integer httpsPort;

	@Value("${server.http.port}")
	private Integer httpPort;

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() { ... }

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(connector());
		return tomcat;
	}

	@Bean
	public Connector connector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setSecure(false);
		connector.setRedirectPort(httpsPort);
		return connector;
	}

}
