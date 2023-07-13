package com.simpledesign.ndms.configuration;

import com.simpledesign.ndms.common.IsDefaultInfoSetPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class InitializePathConfig {
    @Profile({"mac-local"})
    @Bean
    public IsDefaultInfoSetPath macPath() {
        return new IsDefaultInfoSetPath("/Users/dev-sincerity/Desktop/work/satech/resouces/ndms");
    }

    @Profile({"local"})
    @Bean
    public IsDefaultInfoSetPath winLocalPath() {
        return new IsDefaultInfoSetPath("C:/DEV/was_safety_ndms");
    }

    @Profile({
            "dev",
            "prod-43110",
            "prod-43720",
            "prod-43740",
            "prod-43745",
            "prod-43750",
            "prod-43760",
            "prod-43770",
            "prod-44200",
            "test-43110",
            "test-43720",
            "test-43740",
            "test-43745",
            "test-43750",
            "test-43760",
            "test-43770",
            "test-44200"
    })
    @Bean
    public IsDefaultInfoSetPath winPath() {
        return new IsDefaultInfoSetPath("C:/DEV/was_safety_ndms");
    }

}
