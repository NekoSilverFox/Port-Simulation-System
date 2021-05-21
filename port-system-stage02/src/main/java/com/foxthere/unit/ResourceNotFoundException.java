/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/5/21 14:04
 * @Author : NekoSilverfox
 * @FileName: ResourceNotFoundException
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.unit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//定义一个自定义异常，抛出时返回状态码404
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    //
}