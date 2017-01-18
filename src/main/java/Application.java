import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 项目启动类
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 10/24/2016 3:52 PM
 */
@Configuration
@ComponentScan(basePackages = { "com.freedom.weixin" })
@EnableAutoConfiguration
@PropertySource("classpath:application.yml")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
