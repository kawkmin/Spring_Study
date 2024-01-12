package hello.proxy;

import hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)
@Import(AppV2Config.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app.v3") //주의 3.0 버전엔 @Controller 필수화
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

}
