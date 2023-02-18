package hello.basic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "hello.basic.member", //default = @ComponentScan의 패키지 위치
    basePackageClasses = AutoAppConfig.class, // default = @ComponentScan의 클래스
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
    // APPConfig의 @configuration은 @component가 있으니, 제외 해주자
    // 그 외에도 @Controller @Sevice 등 더 있다.
)
public class AutoAppConfig {

}
