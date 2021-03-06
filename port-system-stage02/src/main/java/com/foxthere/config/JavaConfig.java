/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/26 13:41
 * @Author : NekoSilverfox
 * @FileName: JavaConfig
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 *
 *
 * 一个很好的HTML代码转换网站：
 * http://www.jsons.cn/editor/
 */
package com.foxthere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ImportResource("classpath:springmvc-servlet.xml")
public class JavaConfig {

    // 注册一个Bean，就相当于我们之前写的一个Bean标签
    // 这个方法的名字，就相当于Bean标签的属性
    // 这个地方的返回值，就相当于Bean标签中的class属性
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
