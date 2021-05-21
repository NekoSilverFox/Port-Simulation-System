/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 20:44
 * @Author : NekoSilverfox
 * @FileName: InfoGenerator
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service1;

import com.foxthere.model.ConstantsTable;
import com.foxthere.model.TypeGoods;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InfoGenerator {
    /**
     * 【时间点】生成实际的到达时间
     * [Точка во времени] Генерировать фактическое время прибытия
     *
     * @param estimatedArrivalTime 理论到达时间 Теоретическое время прибытия
     * @return 理论到达时间 + 随机值
     */
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


    /** 【时间片】理论的货船卸货的时间（没有加上在队伍中等待的时间）
     * [Time Slice] Теоретическое время разгрузки грузового судна (без добавления времени ожидания в очереди)
     *
     * @param weightOrNumber  货物的重量 Вес товара
     * @param craneEfficiency 起重机的效率（处理每单位物品的时间（ms）） Эффективность крана (время на обработку единицы товара (мс))
     * @return 处理货物需要的【理论】时间（ms）
     */
    public static long estimatedUnloadingTime(int weightOrNumber, long craneEfficiency, int craneNumber) {
        if (craneNumber > ConstantsTable.MAX_CRANE_NUM_FOR_ONE_FREIGHTER) {
            throw new IndexOutOfBoundsException("[ERROR] Crane number can not bigger than " + ConstantsTable.MAX_CRANE_NUM_FOR_ONE_FREIGHTER);
        }
        return (long) weightOrNumber * craneEfficiency / craneNumber;
    }


    /**
     * 【时间片】生成实际的卸货时间（带有随机值的毫秒值）
     * [Time Slice] Генерирование фактического времени выгрузки (миллисекундные значения со случайными величинами)
     *
     * @param estimatedStopTime 处理货物需要的理论时间（ms） Теоретическое время, необходимое для обработки товаров (мс)
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
     * Сгенерируйте случайное название для корабля (с тремя случайными цифрами в конце)
     *
     * @return 生成的随机名字 Генерирование случайных имен
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
     * Генерировать случайные типы товаров, которые хранятся в классе TypeGoods
     *
     * @return 随机货物类型 Произвольный тип груза
     */
    public static TypeGoods randomTypeGoods() {
        int randomIndex = new Random().nextInt(TypeGoods.class.getEnumConstants().length);
        return TypeGoods.class.getEnumConstants()[randomIndex];
    }


    /**
     * 取得一个随机的集装箱数量
     * Получение случайного числа контейнеров
     *
     * @return 随机的集装箱数量   Случайное количество контейнеров
     */
    public static int randomTEUNumber() {
        return new Random()
                .nextInt(ConstantsTable.MAX_TEU_TRANSPORTED - ConstantsTable.MIN_TEU_TRANSPORTED + 1)
                + ConstantsTable.MIN_TEU_TRANSPORTED;
    }


    /**
     * 取得一个随机的散货/液体运输吨数
     * Получение случайного тоннажа сыпучих/жидких грузов
     *
     * @return 随机的散货/液体运输吨数   Произвольный тоннаж сыпучих/жидких грузов
     */
    public static int randomTONNumber() {
        return new Random()
                .nextInt(ConstantsTable.MAX_TON_TRANSPORTED - ConstantsTable.MIN_TON_TRANSPORTED + 1)
                + ConstantsTable.MIN_TON_TRANSPORTED;
    }
}
