/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 17:39
 * @Author : NekoSilverfox
 * @FileName: FreighterTimetable
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service1;

import com.foxthere.pojo.defines.ConstantsTable;

public class FreighterTimetable {
    public static void main(String[] args) {
        System.out.println(ConstantsTable.TABLE_LINE);
//        System.out.printf("%-10s", "船舶的名字", "%-20s", "货物类型", "%-3 s", "预计抵达时间");
        System.out.printf(String.format("%-20s", "船舶的名字") + String.format("%-20s", "货物类型"));
    }
}
