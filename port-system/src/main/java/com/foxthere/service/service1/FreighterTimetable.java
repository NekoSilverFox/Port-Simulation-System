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
import com.foxthere.pojo.defines.Freighter;
import com.foxthere.pojo.defines.TypeGoods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UnknownFormatConversionException;

public class FreighterTimetable {
    /**
     * 储存货轮的 ArrayList，存储有所有类型的货轮
     * ArrayList хранение всех типов грузовых судов
     */
    private ArrayList<Freighter> freighterList;

    /**
     * 集装箱货船
     * контейнерное судно
     */
    private ArrayList<Freighter> containershipList;

    /**
     * 散货船
     * насыпной транспортёр
     */
    private ArrayList<Freighter> bulkCarrierList;

    /**
     * 液货船
     * наливное судно
     */
    private ArrayList<Freighter> tankerList;

    public FreighterTimetable() {
        this.freighterList = new ArrayList<>();
        this.containershipList = new ArrayList<>();
        this.bulkCarrierList = new ArrayList<>();
        this.tankerList = new ArrayList<>();
    }

    public FreighterTimetable(ArrayList<Freighter> freighterList) {
        this.freighterList = freighterList;
        this.freighterList.sort(this::defaultSortingRules);
    }

    public ArrayList<Freighter> getFreighterList() {
        return freighterList;
    }

    /**
     * 设置存储货轮的表格，并且按照时间排序
     * Установить таблицу для хранения грузовых кораблей и сортировать их по времени
     * @param freighterList 货轮的表格
     */
    public void setFreighterList(ArrayList<Freighter> freighterList) {
        this.freighterList = freighterList;
        this.containershipList = new ArrayList<>();
        this.bulkCarrierList = new ArrayList<>();
        this.tankerList = new ArrayList<>();

        for (Freighter freighter : freighterList) {
            switch (freighter.getTypeGoods()) {
                case CONTAINER -> {
                    containershipList.add(freighter);
                    break;
                }
                case BULK_CARGO -> {
                    bulkCarrierList.add(freighter);
                    break;
                }
                case LIQUID -> {
                    tankerList.add(freighter);
                    break;
                }
                default -> {
                    throw new UnknownFormatConversionException("[ERROR] Unknown good type");
                }
            }
        }

        this.freighterList.sort(this::defaultSortingRules);
        this.containershipList.sort(this::defaultSortingRules);
        this.bulkCarrierList.sort(this::defaultSortingRules);
        this.tankerList.sort(this::defaultSortingRules);
    }


    /**
     * 添加一个货轮到表中
     *
     * @param freighter 要添加的货轮
     */
    public void addFreighter(Freighter freighter) {
        if ((freighter == null) || freighter.isIncompleteInfo()) {
            throw new NullPointerException("[ERROR] Object freighter can not be null or incomplete information");
        }

        this.freighterList.add(freighter);
        this.freighterList.sort(this::defaultSortingRules);

        switch (freighter.getTypeGoods()) {
            case CONTAINER -> {
                this.containershipList.add(freighter);
                this.containershipList.sort(this::defaultSortingRules);
                return;
            }
            case BULK_CARGO -> {
                this.bulkCarrierList.add(freighter);
                this.bulkCarrierList.sort(this::defaultSortingRules);
                return;
            }
            case LIQUID -> {
                this.tankerList.add(freighter);
                this.tankerList.sort(this::defaultSortingRules);
                return;
            }
            default -> {
                throw new UnknownFormatConversionException("[ERROR] Unknown good type");
            }
        }

    }


    /**
     * 默认排序方式，按照【实际】的抵达时间
     * Метод сортировки по умолчанию, по [фактическому] времени прибытия.
     * @param l 左值
     * @param r 右值
     * @return int 类型的排序规则（升序）
     */
    private int defaultSortingRules(Freighter l, Freighter r) {
        return (int) (l.getActualArrivalTime() - r.getActualArrivalTime());
    }


    /**
     * 获取货轮的总量（包含所有类型）
     * Получить общее количество грузовых судов (со всеми типами).
     * @return 获取货轮的总量（包含所有类型）
     */
    public int allFreighterNumber() {
        return this.freighterList.size();
    }


    /**
     * 获取【集装箱】货轮的总量
     * Получить общее количество грузовых судов [контейнеровозов].
     * @return 货轮的总量（集装箱）
     */
    public int containershipNumber() {
        return this.containershipList.size();
    }


    /**
     * 获取【散货船】的总量
     * Получить общее количество [насыпных грузов].
     * @return 获取货轮的总量（散货船）
     */
    public int bulkCarrierNumber() {
        return this.bulkCarrierList.size();
    }


    /**
     * 获取【液货船】的总量
     *
     * @return 获取货轮的总量（液货船）
     */
    public int tankerNumber() {
        return this.tankerList.size();
    }


    /**
     * 平均等待时长
     *
     * @return 平均等待时长
     */
    public long averageWaitingTime() {
        // TODO
        return 0;
    }


    /**
     * 所有类型货轮的总罚款
     *
     * @return 总罚款
     */
    public int allFreighterTotalFine() {
        int totalFine = 0;

        for (Freighter freighter : this.freighterList) {
            totalFine += freighter.getFine();
        }

        return totalFine;
    }


    /**
     * 所有集装箱货船的总罚款
     *
     * @return 总罚款
     */
    public int containershipTotalFine() {
        int totalFine = 0;

        for (Freighter freighter : this.containershipList) {
            totalFine += freighter.getFine();
        }

        return totalFine;
    }


    /**
     * 所有散货船的总罚款
     *
     * @return 总罚款
     */
    public int bulkCarrierTotalFine() {
        int totalFine = 0;

        for (Freighter freighter : this.bulkCarrierList) {
            totalFine += freighter.getFine();
        }

        return totalFine;
    }


    /**
     * 所有液货船的总罚款
     *
     * @return 总罚款
     */
    public int tankerTotalFine() {
        int totalFine = 0;

        for (Freighter freighter : this.tankerList) {
            totalFine += freighter.getFine();
        }

        return totalFine;
    }


    /**
     * 按照索引从所有货轮列表中获取货轮
     *
     * @param index
     * @return
     */
    public Freighter get(int index) {
        if ((index >= this.freighterList.size()) || (index < 0)) {
            throw new IndexOutOfBoundsException("[ERROR] Index can not small than 0 or bigger than number of freighter");
        }

        return this.freighterList.get(index);
    }

    public ArrayList<Freighter> getContainershipList() {
        return containershipList;
    }

    public ArrayList<Freighter> getBulkCarrierList() {
        return bulkCarrierList;
    }

    public ArrayList<Freighter> getTankerList() {
        return tankerList;
    }

    /**
     * 生成船的时刻表
     * TODO 实现用动态规划生成时间等待
     *
     * @param freighterArrivalInterval 平均每隔多久到一艘船
     * @param durationSimulation       模拟的时长
     * @return 生成的船的时刻表
     */
    public ArrayList<Freighter> createFreighterList(long freighterArrivalInterval, long durationSimulation) {
        System.out.println("[INFO] Start to create freighter time table");

        long startTime = System.currentTimeMillis();
        long nowTime = startTime;
        long endTime = startTime + durationSimulation;

        while (nowTime < endTime) {
            Freighter freighter = new Freighter();

            freighter.setName(InfoGenerator.randomName());
            freighter.setTypeGoods(InfoGenerator.randomTypeGoods());
            freighter.setEstimatedArrivalTime(nowTime);
            freighter.setActualArrivalTime(InfoGenerator.randomActualArrivalTime(freighter.getEstimatedArrivalTime()));

            if (freighter.getTypeGoods() == TypeGoods.CONTAINER) {
                this.containershipList.add(freighter);

                freighter.setWeightOrNumber(InfoGenerator.randomTEUNumber());
                freighter.setEstimatedStopTime(InfoGenerator.estimatedUnloadingTime(freighter.getWeightOrNumber(),
                        ConstantsTable.CRANE_REQUIRED_PROCESS_ONE_TEU,
                        ConstantsTable.DEFAULT_CRANE_NUM_FOR_ONE_FREIGHTER));

            } else if (freighter.getTypeGoods() == TypeGoods.BULK_CARGO) {
                this.bulkCarrierList.add(freighter);

                freighter.setWeightOrNumber(InfoGenerator.randomTONNumber());
                freighter.setEstimatedStopTime(InfoGenerator.estimatedUnloadingTime(freighter.getWeightOrNumber(),
                        ConstantsTable.CRANE_REQUIRED_PROCESS_ONE_TON,
                        ConstantsTable.DEFAULT_CRANE_NUM_FOR_ONE_FREIGHTER));

            } else if (freighter.getTypeGoods() == TypeGoods.LIQUID) {
                this.tankerList.add(freighter);

                freighter.setWeightOrNumber(InfoGenerator.randomTONNumber());
                freighter.setEstimatedStopTime(InfoGenerator.estimatedUnloadingTime(freighter.getWeightOrNumber(),
                        ConstantsTable.CRANE_REQUIRED_PROCESS_ONE_TON,
                        ConstantsTable.DEFAULT_CRANE_NUM_FOR_ONE_FREIGHTER));

            } else {
                throw new UnknownFormatConversionException("[ERROR] Unknown good type");
            }

            freighter.setActualStopTime(InfoGenerator.randomActualUnloadingTime(freighter.getEstimatedStopTime()));

            // 与预计完成卸货时间的差距（单位：ms）
            long unloadDelayTime = freighter.getActualStopTime() - freighter.getEstimatedStopTime();
            int stayTime_Hour = (int) (unloadDelayTime / 1000 / 60 / 60);
            if (stayTime_Hour <= 0) {
                freighter.setFine(0);
            } else {
                freighter.setFine(stayTime_Hour * ConstantsTable.FINE_EVERY_HOUR);
            }

            this.freighterList.add(freighter);
            nowTime += freighterArrivalInterval;
        }

        // 按照【实际】到达日期将其排序
        this.freighterList.sort(this::defaultSortingRules);
        this.containershipList.sort(this::defaultSortingRules);
        this.bulkCarrierList.sort(this::defaultSortingRules);
        this.tankerList.sort(this::defaultSortingRules);

        System.out.println("[INFO] Successful to create freighter time table");
        return this.freighterList;
    }


    /**
     * 以列表的形式打印【所有】货轮的时间表
     *
     * @param timeType 显示时间的格式
     */
    public void printAllFreighterTimetable(String timeType) {
        FreighterTimetable.printFreighterTimetable(this.freighterList, timeType);
    }


    /**
     * 以列表的形式打印【集装箱】货轮的时间表
     *
     * @param timeType 显示时间的格式
     */
    public void printContainershipTimetable(String timeType) {
        FreighterTimetable.printFreighterTimetable(this.containershipList, timeType);
    }


    /**
     * 以列表的形式打印【散货船】的时间表
     *
     * @param timeType 显示时间的格式
     */
    public void printBulkCarrierTimetable(String timeType) {
        FreighterTimetable.printFreighterTimetable(this.bulkCarrierList, timeType);
    }


    /**
     * 以列表的形式打印【液货船】的时间表
     *
     * @param timeType 显示时间的格式
     */
    public void printTankerTimetable(String timeType) {
        FreighterTimetable.printFreighterTimetable(this.tankerList, timeType);
    }

    /**
     * 以列表的形式打印指定类型货轮的时间表
     *
     * @param freighterTimetable 存储货轮的 ArrayList
     * @param timeType           显示时间的格式
     */
    public static void printFreighterTimetable(ArrayList<Freighter> freighterTimetable, String timeType) {
        if ((freighterTimetable == null) || (freighterTimetable.size() == 0)) {
            System.out.println("[INFO] Freighter time table is empty");
            return;
        }

        System.out.println(ConstantsTable.TABLE_LINE);

        System.out.println(String.format("%-13s", "Name")
                + String.format("%-20s", "Type goods")
                + String.format("%-20s", "Arrival time")
                + String.format("%-20s", "Waiting time")
                + String.format("%-25s", "Unload start time")
                + String.format("%-25s", "Estimated stop time")
                + String.format("%-20s", "Actual stop time")
                + String.format("%-20s", "Fine"));

        System.out.println(ConstantsTable.TABLE_LINE);

        for (Freighter freighter : freighterTimetable) {
            System.out.println(String.format("%-13s", freighter.getName())
                    + String.format("%-20s", freighter.getTypeGoods().toString().toLowerCase(Locale.ROOT))
                    + String.format("%-20s", new SimpleDateFormat(timeType).format(new Date(freighter.getActualArrivalTime())))
                    + String.format("%-20s", freighter.getWaitingTimeInQueue() / 1000 / 60 + " min")
                    + String.format("%-25s", new SimpleDateFormat(timeType).format(freighter.getActualArrivalTime() + freighter.getWaitingTimeInQueue()))
                    + String.format("%-25s", freighter.getEstimatedStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", freighter.getActualStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", "$" + freighter.getFine())
            );
        }

        System.out.println(ConstantsTable.TABLE_LINE);
    }
}
