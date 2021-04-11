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

import java.util.Objects;

public class Freighter {
    /** 货船的名字 */
    private String name;

    /** 货船搭载的货物类型 */
    private TypeGoods typeGoods;

    /** 载货重量，对于 集装箱（单位：件数），对于液体和散货（单位：吨） */
    private int weightOrNumber;

    /** 预计抵达时间（毫秒值） */
    private long estimatedArrivalTime;

    /** 实际抵达时间（毫秒值） */
    private long actualArrivalTime;

    /** 预计停靠时间（卸货的时间） 单位：ms */
    private long estimatedStopTime;

    /** 实际停靠时间（卸货的时间） 单位：ms */
    private long actualStopTime;

    public Freighter() {
    }

    public Freighter(String name, TypeGoods typeGoods, int weightOrNumber, long estimatedArrivalTime, long actualArrivalTime, long estimatedStopTime, long actualStopTime) {
        this.name = name;
        this.typeGoods = typeGoods;
        this.weightOrNumber = weightOrNumber;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.estimatedStopTime = estimatedStopTime;
        this.actualStopTime = actualStopTime;
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
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public long getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(long actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public long getEstimatedStopTime() {
        return estimatedStopTime;
    }

    public void setEstimatedStopTime(long estimatedStopTime) {
        this.estimatedStopTime = estimatedStopTime;
    }

    public long getActualStopTime() {
        return actualStopTime;
    }

    public void setActualStopTime(long actualStopTime) {
        this.actualStopTime = actualStopTime;
    }
}
