package com.tttare.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;

/**
 * @author verywell
 * @date 2018/5/31.
 * spring WebMvc配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

   /* @Value("${hxx.imagePath}")
    private String imagePath;
    @Value("${hxx.uploadPath}")
    private String filePath;*/

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }


//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    	System.out.println("----"+this.getMessageConverters().size());
////        converters.add(responseBodyConverter());
//        //增加controller响应消息json转换器；在controller直接返回对象可自动转换为json
////        converters.add(new MappingJackson2HttpMessageConverter(new ConfigurableObjectMapper()));
//        super.configureMessageConverters(converters);
//        System.out.println("-----"+converters.size());
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(0);
        registry.addResourceHandler("/tttare/**").addResourceLocations("file:///E:/书籍/tttare/");
        super.addResourceHandlers(registry);
    }

    /*保留国际化*/
   /* @Bean
    public LocaleChangeInterceptor interceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }*/

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
        registry.addInterceptor(new UrlResolveInterceptor());
    }*/

    /*@Bean
    public LocaleResolver resolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }*/
}
