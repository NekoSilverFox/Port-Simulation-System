/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 20:44
 * @Author : NekoSilverfox
 * @FileName: InfoGenerator
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service1;

import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.TypeGoods;

import java.util.Random;

public class InfoGenerator {
    public static long randomActualArrivalTime(long estimatedArrivalTime) {
        // 如果生成的是 true 则提前到达，如果是 false 则延迟到达
        if (new Random().nextBoolean()) {
            long randomTimeDeviation = (-1) * Math.abs(new Random().nextLong()) % ConstantsTable.EARLY_ARRIVAL_TIME;
            return estimatedArrivalTime + randomTimeDeviation;
        } else {
            long randomTimeDeviation = Math.abs(new Random().nextLong()) % ConstantsTable.DELAYED_ARRIVAL_TIME;
            return estimatedArrivalTime + randomTimeDeviation;
        }
    }


    /** 理论的货船卸货的时间（没有加上在队伍中等待的时间）
     * @param weightOrNumber  货物的重量
     * @param craneEfficiency 起重机的效率（处理每单位物品的时间（ms））
     * @return 处理货物需要的【理论】时间（ms）
     */
    public static long estimatedUnloadingTime(int weightOrNumber, long craneEfficiency) {
        return (long) weightOrNumber * craneEfficiency;  // TODO 增加起重机数量（Max 2）
    }


    /**
     * 生成实际的卸货时间（带有随机值的毫秒值）
     *
     * @param estimatedStopTime 处理货物需要的理论时间（ms）
     * @return 处理货物需要的【实际】时间（ms）
     */
    public static long randomActualUnloadingTime(long estimatedStopTime) {
        long randomTimeDeviation =
                Math.abs(
                        new Random().nextLong())
                        % ConstantsTable.DELAYED_TIME_COMPLETION_UNLOADING
                        + ConstantsTable.EARLY_TIME_COMPLETION_UNLOADING;
        return estimatedStopTime + randomTimeDeviation;
    }


    /**
     * 生成一个船舶的随机名字（尾部带三位随机数字）
     * @return 生成的随机名字
     */
    public static String randomName() {
        int lenName = new Random().nextInt(3) + 3;
        StringBuffer name = new StringBuffer();

        // 生成大写的首字母
        char initials = (char) (new Random().nextInt(26) + 65);
        name.append(initials);

        // 生成首字母后的随机字母
        for (int i = 0; i < lenName - 1; i++) {
            char letter = (char) (new Random().nextInt(26) + 97);
            name.append(letter);
        }

        // 生成3位尾部的随机数字
        for (int i = 0; i < ConstantsTable.NUM_DIGITS_END_OF_NAME; i++) {
            name.append(new Random().nextInt(10));
        }

        return name.toString();
    }


    /**
     * 生成随机的货物类型，货物的类型存储在TypeGoods类中
     * @return
     */
    public static TypeGoods randomTypeGoods() {
        int randomIndex = new Random().nextInt(TypeGoods.class.getEnumConstants().length);
        return TypeGoods.class.getEnumConstants()[randomIndex];
    }


    /**
     * 取得一个随机的集装箱数量
     * @return 随机的集装箱数量
     */
    public static int randomTEUNumber() {
        return new Random()
                .nextInt(ConstantsTable.MAX_TEU_TRANSPORTED - ConstantsTable.MIN_TEU_TRANSPORTED + 1)
                + ConstantsTable.MIN_TEU_TRANSPORTED;
    }


    /**
     * 取得一个随机的散货/液体运输吨数
     * @return 随机的散货/液体运输吨数
     */
    public static int randomTONNumber() {
        return new Random()
                .nextInt(ConstantsTable.MAX_TON_TRANSPORTED - ConstantsTable.MIN_TON_TRANSPORTED + 1)
                + ConstantsTable.MIN_TON_TRANSPORTED;
    }
}
