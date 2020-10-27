package com.intertek.demo.common.configure;

import com.intertek.demo.common.resolver.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author barry.jt.huang
 */
@Configuration
public class LocaleConfig {
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Bean
    public WebMvcConfigurer localeInterceptor(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
                localeChangeInterceptor.setParamName("lang");
                registry.addInterceptor(localeChangeInterceptor);
            }
        };
    }
}
