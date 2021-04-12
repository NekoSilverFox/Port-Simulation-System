/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 15:05
 * @Author : NekoSilverfox
 * @FileName: Freighter
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.pojo.defines;

import java.util.ArrayList;
import java.util.Objects;

public class Freighter {
    /**
     * 货船的名字
     */
    private String name;

    /**
     * 货船搭载的货物类型
     */
    private TypeGoods typeGoods;

    /**
     * 载货重量，对于 集装箱（单位：件数），对于液体和散货（单位：吨）
     */
    private int weightOrNumber;

    /**
     * 预计抵达时间（毫秒值）
     */
    private long estimatedArrivalTime;

    /**
     * 实际抵达时间（毫秒值）
     */
    private long actualArrivalTime;

    /**
     * 预计停靠时间（卸货的时间） 单位：ms
     */
    private long estimatedStopTime;

    /**
     * 实际停靠时间（卸货的时间） 单位：ms
     */
    private long actualStopTime;

    /**
     * 罚款 单位：美元
     */
    private int fine;

    public Freighter() {
    }

    public Freighter(String name, TypeGoods typeGoods, int weightOrNumber, long estimatedArrivalTime, long actualArrivalTime, long estimatedStopTime, long actualStopTime, int fine) {
        this.name = name;
        this.typeGoods = typeGoods;
        this.weightOrNumber = weightOrNumber;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.estimatedStopTime = estimatedStopTime;
        this.actualStopTime = actualStopTime;
        this.fine = fine;
    }

    /**
     * 判断信息是否缺失，如果某个成员变量没有赋值或者异常，则返回 true；否则返回 false
     *
     * @return 是否缺失
     */
    public boolean isIncompleteInfo() {
        if (this.name.isEmpty()
                || (this.typeGoods == null)
                || (this.weightOrNumber < 0)
                || (this.actualArrivalTime < 0)
                || (this.estimatedArrivalTime < 0)
                || (this.estimatedStopTime < 0)
                || (this.actualStopTime < 0)
        ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Freighter freighter = (Freighter) o;
        return Objects.equals(name, freighter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeGoods getTypeGoods() {
        return typeGoods;
    }

    public void setTypeGoods(TypeGoods typeGoods) {
        this.typeGoods = typeGoods;
    }

    public int getWeightOrNumber() {
        return weightOrNumber;
    }

    public void setWeightOrNumber(int weightOrNumber) {
        this.weightOrNumber = weightOrNumber;
    }

    public long getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(long estimatedArrivalTime) {
        if (estimatedArrivalTime < 0) {
            throw new IndexOutOfBoundsException("[ERROR] Estimated arrival time can not small than 0");
        }
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public long getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(long actualArrivalTime) {
        if (actualArrivalTime < 0) {
            throw new IndexOutOfBoundsException("[ERROR] Actual arrival time can not small than 0");
        }
        this.actualArrivalTime = actualArrivalTime;
    }

    public long getEstimatedStopTime() {
        return estimatedStopTime;
    }

    public void setEstimatedStopTime(long estimatedStopTime) {
        if (estimatedStopTime < 0) {
            throw new IndexOutOfBoundsException("[ERROR] Estimated stop time can not small than 0");
        }
        this.estimatedStopTime = estimatedStopTime;
    }

    public long getActualStopTime() {
        return actualStopTime;
    }

    public void setActualStopTime(long actualStopTime) {
        if (actualStopTime < 0) {
            throw new IndexOutOfBoundsException("[ERROR] Actual stop time can not small than 0");
        }
        this.actualStopTime = actualStopTime;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        if (fine < 0) {
            throw new IndexOutOfBoundsException("[ERROR] Fine can not small than 0");
        }
        this.fine = fine;
    }
}
