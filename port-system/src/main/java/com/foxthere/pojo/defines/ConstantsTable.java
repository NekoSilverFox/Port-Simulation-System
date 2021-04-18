/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 15:38
 * @Author : NekoSilverfox
 * @FileName: ConstantsTable
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.pojo.defines;

public class ConstantsTable {
    /** 每台起重机的价格，单位（美元） */
    public static final int CRANE_PRICE = 30000;

    /** 每种起重机初始数量，单位（个） */
    public static final int CRANE_INITIAL_NUM = 1;

    /** 默认为船舶服务的起重机数量，单位（个） */
    public static final int DEFAULT_CRANE_NUM_FOR_ONE_FREIGHTER = 2;

    /** 最多为船舶服务的起重机数量，单位（个） */
    public static final int MAX_CRANE_NUM_FOR_ONE_FREIGHTER = 2;

    /** 起重机的效率，每处理一件所需的平均时间 30箱/小时 ==> 每箱2分钟（单位：ms）*/
    public static final int CRANE_REQUIRED_PROCESS_ONE_TEU = 2 * 60 * 1000;

    /** 起重机的效率，每处理一吨所需的平均时间 1000吨/小时 ==> 每吨0.06分钟 ==> 3600ms/t（单位：ms） */
    public static final int CRANE_REQUIRED_PROCESS_ONE_TON = 3600;

    /** 最小集装箱运输件数 4500TEU */
    public static final int MIN_TEU_TRANSPORTED = 4500;

    /** 最大集装箱运输件数 10000TEU */
    public static final int MAX_TEU_TRANSPORTED = 10000;

    /** 最小散货/液体运输吨数 6000吨 */
    public static final int MIN_TON_TRANSPORTED = 6000;

    /** 最大散货/液体运输吨数 300000吨 */
    public static final int MAX_TON_TRANSPORTED = 300000;

    /** 每多停靠一小时的罚金 */
    public static final int FINE_EVERY_HOUR = 100;

    /** 模拟船舶到港时间的最大提前偏差，-7 天，单位（毫秒 ms） */
    public static final long EARLY_ARRIVAL_TIME = 7L * 24 * 60 * 60 * 1000;

    /** 模拟船舶到港时间的最大延后偏差，+7 天，单位（毫秒 ms） */
    public static final long DELAYED_ARRIVAL_TIME = +7L * 24 * 60 * 60 * 1000;

    /** 模拟船舶卸货完成的最大提前偏差，0 分，单位（毫秒 ms） */
    public static final long EARLY_TIME_COMPLETION_UNLOADING = 0;

    /** 模拟船舶卸货完成的最大延后偏差，1440 分，单位（毫秒 ms） */
    public static final long DELAYED_TIME_COMPLETION_UNLOADING = 1440 * 60 * 1000;

    /** 多长时间到一艘船（抵达间隔），30min，单位（毫秒 ms） */
    public static final long FREIGHTER_ARRIVAL_INTERVAL = 30 * 60 * 1000;

    /** 模拟的时长，30 天，单位（毫秒 ms） */
    public static final long DURATION_SIMULATION = 30L * 24 * 60 * 60 * 1000;

    /** 船舶尾部的n位随机数字 */
    public static final int NUM_DIGITS_END_OF_NAME = 3;

    /** 默认Json文件的存储位置 */
    public static final String JSON_FILE_PATH = "port-system\\src\\main\\java\\com\\foxthere\\data\\TimeTable.json";

    /** 默认 Result 结果(Json文件)存储位置 */
    public static final String RESULT_FILE_PATH = "port-system\\src\\main\\java\\com\\foxthere\\data\\Result.json";

    /** 时间格式 */
    public static final String TIME_TYPE = "MM:dd:HH:mm";

    /** 线程等待时间 */
    public static final int DEFAULT_SLEEP_MS = 5;

    /** 表头底线 */
    public static final String TABLE_LINE = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

}
