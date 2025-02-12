package com.ispan.chufa;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 配置消息源
    @Bean
    public MessageSource messageSource() {
        org.springframework.context.support.ResourceBundleMessageSource messageSource = new org.springframework.context.support.ResourceBundleMessageSource();
        messageSource.setBasename("messages"); // 設置消息屬性文件的基名（不包括 .properties）
        messageSource.setDefaultEncoding("UTF-8"); // 設置字符編碼
        return messageSource;
    }

    // 配置區域解析器
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.TAIWAN); // 設置默認語言為繁體中文（台灣）
        return localeResolver;
    }

    // 配置語言改變攔截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang"); // 設置用戶可以通過?lang=zh_TW來改變語言
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://192.168.50.90:5173") // 允許前端的 IP
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的 HTTP 方法
        .allowedHeaders("*") // 允許所有 Header
        .allowCredentials(true); // 允許攜帶憑證 (如 Cookie)
    }
}
