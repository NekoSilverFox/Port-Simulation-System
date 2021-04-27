/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/12 22:38
 * @Author : NekoSilverfox
 * @FileName: StatisticalResults
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticalResults {
    /**
     * 最佳起重机数量
     * Оптимальное количество кранов
     */
    private int numCrane;

    /**
     * 卸货船只的数量
     * Количество судов, разгружающих груз
     */
    private int numFreighters;

    /**
     * 【时间片】在队伍中平均等待时长（和最大时长 同下） (ms)
     * Среднее время ожидания в очереди (и максимальное время ниже) (мс)
     */
    private long averageWaitingTimeInQueue;

    /**
     * 【时间片】在队伍中最大的卸货等待时间 (ms)
     * Максимальное время ожидания разгрузки в очереди (мс)
     */
    private long maxUnloadingDelayTime;

    /**
     * 【时间片】平均的卸货延迟时间 (ms)
     * Среднее время задержки разгрузки (мс)
     */
    private long averageUnloadingDelayTime;

    /**
     * 【时间片】卸货的平均时长 (ms)
     * Среднее время на разгрузку (мс)
     */
    private long averageTimeOfUnloading;

    /**
     * 总罚款
     * Общие штрафы
     */
    private long totalFine;

    public StatisticalResults() {
    }

    public StatisticalResults(int numCrane, int numFreighters, long averageWaitingTimeInQueue, long maxUnloadingDelayTime,
                              long averageUnloadingDelayTime, long averageTimeOfUnloading, long totalFine) {
        this.numCrane = numCrane;
        this.numFreighters = numFreighters;
        this.averageWaitingTimeInQueue = averageWaitingTimeInQueue;
        this.maxUnloadingDelayTime = maxUnloadingDelayTime;
        this.averageUnloadingDelayTime = averageUnloadingDelayTime;
        this.averageTimeOfUnloading = averageTimeOfUnloading;
        this.totalFine = totalFine;
    }

    @Override
    public String toString() {
        return "StatisticalResults{" +
                "numCrane=" + numCrane +
                ", numFreighters=" + numFreighters +
                ", averageWaitingTimeInQueue=" + averageWaitingTimeInQueue +
                ", maxUnloadingDelayTime=" + maxUnloadingDelayTime +
                ", averageUnloadingDelayTime=" + averageUnloadingDelayTime +
                ", averageTimeOfUnloading=" + averageTimeOfUnloading +
                ", totalFine=" + totalFine +
                '}';
    }

    public int getNumCrane() {
        return numCrane;
    }

    public void setNumCrane(int numCrane) {
        this.numCrane = numCrane;
    }

    public int getNumFreighters() {
        return numFreighters;
    }

    public void setNumFreighters(int numFreighters) {
        this.numFreighters = numFreighters;
    }

    public long getAverageWaitingTimeInQueue() {
        return averageWaitingTimeInQueue;
    }

    public void setAverageWaitingTimeInQueue(long averageWaitingTimeInQueue) {
        this.averageWaitingTimeInQueue = averageWaitingTimeInQueue;
    }

    public long getMaxUnloadingDelayTime() {
        return maxUnloadingDelayTime;
    }

    public void setMaxUnloadingDelayTime(long maxUnloadingDelayTime) {
        this.maxUnloadingDelayTime = maxUnloadingDelayTime;
    }

    public long getAverageUnloadingDelayTime() {
        return averageUnloadingDelayTime;
    }

    public void setAverageUnloadingDelayTime(long averageUnloadingDelayTime) {
        this.averageUnloadingDelayTime = averageUnloadingDelayTime;
    }

    public long getAverageTimeOfUnloading() {
        return averageTimeOfUnloading;
    }

    public void setAverageTimeOfUnloading(long averageTimeOfUnloading) {
        this.averageTimeOfUnloading = averageTimeOfUnloading;
    }

    public long getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(long totalFine) {
        this.totalFine = totalFine;
    }
}
