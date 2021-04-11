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

public class FreighterTimetable {
    /** 储存货轮的 ArrayList，存储有所有类型的货轮 */
    private ArrayList<Freighter> freighterList;

    // TODO 增加几种船舶的队列

    public FreighterTimetable() {
        this.freighterList = new ArrayList<>();
    }

    public FreighterTimetable(ArrayList<Freighter> freighterList) {
        this.freighterList = freighterList;
    }

    public ArrayList<Freighter> getFreighterList() {
        return freighterList;
    }

    public void setFreighterList(ArrayList<Freighter> freighterList) {
        this.freighterList = freighterList;
    }

    /** TODO
     * 添加一个货轮到表中
     * @param freighter 要添加的货轮
     */
    public void addFreighter(Freighter freighter) {
        if ((freighter == null) || freighter.isIncompleteInfo()) {
            throw new NullPointerException("[ERROR] Object freighter can not be null or incomplete information");
        } else {
            // 添加到容器中 TODO
        }
    }

    /**
     * 获取货轮的总量（包含所有类型）
     * @return 获取货轮的总量（包含所有类型）
     */
    public int freighterNumber() {
        return this.freighterList.size();
    }

    /**
     * 平均等待时长
     * @return 平均等待时长
     */
    public long averageWaitingTime() {
        // TODO
        return 0;
    }


    /**
     * 总罚款
     * @return 总罚款
     */
    public int totalFine() {
        int totalFine = 0;

        for (Freighter freighter : this.freighterList) {
            totalFine += freighter.getFine();
        }

        return totalFine;
    }



    public Freighter get(int index) {
        if ((index >= this.freighterList.size()) || (index < 0)) {
            throw new IndexOutOfBoundsException("[ERROR] Index can not small than 0 or bigger than number of freighter");
        }

        return this.freighterList.get(index);
    }

    /**
     * 生成船的时刻表
     * TODO 实现用动态规划生成时间等待
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
                freighter.setWeightOrNumber(InfoGenerator.randomTEUNumber());
                freighter.setEstimatedStopTime(InfoGenerator.estimatedStopTime(freighter.getWeightOrNumber(),
                        ConstantsTable.CRANE_REQUIRED_PROCESS_ONE_TEU));
            } else {
                freighter.setWeightOrNumber(InfoGenerator.randomTONNumber());
                freighter.setEstimatedStopTime(InfoGenerator.estimatedStopTime(freighter.getWeightOrNumber(),
                        ConstantsTable.CRANE_REQUIRED_PROCESS_ONE_TON));
            }
            freighter.setActualStopTime(InfoGenerator.randomActualStopTime(freighter.getEstimatedStopTime()));

            int stayTime_Hour = (int) ((freighter.getActualStopTime() - freighter.getEstimatedStopTime()) / 1000 / 60 / 60);
            if (stayTime_Hour <= 0) {
                freighter.setFine(0);
            } else {
                freighter.setFine(stayTime_Hour * ConstantsTable.FINE_EVERY_HOUR);
            }

            this.freighterList.add(freighter);
            nowTime += freighterArrivalInterval;
        }

        System.out.println("[INFO] Successful to create freighter time table");
        return this.freighterList;
    }

    public void printFreighterList(String timeType) {
        if (this.freighterList.size() == 0) {
            System.out.println("[INFO] Freighter time table is empty");
            return;
        }

        System.out.println(String.format("%-13s", "Name")
                + String.format("%-20s", "Type goods")
                + String.format("%-20s", "Arrival time")
                + String.format("%-25s", "Estimated stop time")
                + String.format("%-20s", "Actual stop time")
                + String.format("%-20s", "Fine"));

        System.out.println(ConstantsTable.TABLE_LINE);

        for (Freighter freighter : this.freighterList) {
            System.out.println(String.format("%-13s", freighter.getName())
                    + String.format("%-20s", freighter.getTypeGoods().toString().toLowerCase(Locale.ROOT))
                    + String.format("%-20s", new SimpleDateFormat(timeType).format(new Date(freighter.getActualArrivalTime())))
                    + String.format("%-25s", freighter.getEstimatedStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", freighter.getActualStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", "$ " + freighter.getFine()));
        }

        System.out.println(ConstantsTable.TABLE_LINE);
    }

    public static void printFreighterList(FreighterTimetable freighterTimetable, String timeType) {
        if ((freighterTimetable == null) || (freighterTimetable.freighterList.size() == 0)) {
            System.out.println("[INFO] Freighter time table is empty");
            return;
        }

        System.out.println(String.format("%-13s", "Name")
                + String.format("%-20s", "Type goods")
                + String.format("%-20s", "Arrival time")
                + String.format("%-25s", "Estimated stop time")
                + String.format("%-20s", "Actual stop time")
                + String.format("%-20s", "Fine"));

        System.out.println(ConstantsTable.TABLE_LINE);

        for (int i = 0; i < freighterTimetable.freighterNumber(); i++) {
            System.out.println(String.format("%-13s", freighterTimetable.get(i).getName())
                    + String.format("%-20s", freighterTimetable.get(i).getTypeGoods().toString().toLowerCase(Locale.ROOT))
                    + String.format("%-20s", new SimpleDateFormat(timeType).format(new Date(freighterTimetable.get(i).getActualArrivalTime())))
                    + String.format("%-25s", freighterTimetable.get(i).getEstimatedStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", freighterTimetable.get(i).getActualStopTime() / 1000 / 60 + " min")
                    + String.format("%-20s", "$ " + freighterTimetable.get(i).getFine()));
        }

        System.out.println(ConstantsTable.TABLE_LINE);
    }
}
