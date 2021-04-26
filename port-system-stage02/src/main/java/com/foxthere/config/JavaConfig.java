/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/26 13:41
 * @Author : NekoSilverfox
 * @FileName: JavaConfig
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:springmvc-servlet.xml")
public class JavaConfig {

}
