package com.simpledesign.ndms.configuration;

import com.simpledesign.ndms.interceptor.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiKeyInterceptor apiKeyInterceptor;

    public WebConfig(ApiKeyInterceptor apiKeyInterceptor) {
        this.apiKeyInterceptor = apiKeyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .excludePathPatterns("/api/exclude") // 인터셉터에서 제외할 경로
//                .excludePathPatterns("/api/dsMng/setDs") // 인터셉터에서 제외할 경로
//                .excludePathPatterns("/api/cctvMng/setCctv") // 인터셉터에서 제외할 경로
                .excludePathPatterns("/api/*/set*") // 인터셉터에서 제외할 경로
                .excludePathPatterns("/api/test/*/set*") // 인터셉터에서 제외할 경로
                .addPathPatterns("/api/**") // 인터셉터에서 처리할 경로
                .order(1); // 인터셉터 실행 순서
    }
}
