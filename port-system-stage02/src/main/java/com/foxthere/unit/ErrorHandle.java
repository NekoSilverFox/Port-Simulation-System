/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/27 0:40
 * @Author : NekoSilverfox
 * @FileName: ErrorHandle
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.unit;

public class ErrorHandle {
    public static void throwError() {
        try {
            String url = "https://foxthere.com/error";
            java.net.URI uri = java.net.URI.create(url);

            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();

            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                dp.browse(uri);// 获取系统默认浏览器打开链接
            }
        } catch (java.lang.NullPointerException e) {
            // 此为uri为空时抛出异常
            e.printStackTrace();
        } catch (java.io.IOException e) {
            // 此为无法获取系统默认浏览器
            e.printStackTrace();
        }
    }

}

