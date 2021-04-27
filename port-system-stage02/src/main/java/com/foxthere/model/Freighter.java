/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 15:05
 * @Author : NekoSilverfox
 * @FileName: Freighter
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Freighter {
    /**
     * 货船的名字
     * Наименование грузового судна
     */
    private String name;

    /**
     * 货船搭载的货物类型
     * Вид груза, перевозимого грузовыми судами
     */
    private TypeGoods typeGoods;

    /**
     * 载货重量，对于 集装箱（单位：件数），对于液体和散货（单位：吨）
     * Грузоподъемность, для контейнеров (в штуках), для наливных и насыпных грузов (в тоннах)
     */
    private int weightOrNumber;

    /**
     * 【时间点】预计抵达时间（毫秒值）
     * Расчетное время прибытия (значения в миллисекундах)
     */
    private long estimatedArrivalTime;

    /**
     * 【时间点】实际抵达时间（毫秒值）
     * Фактическое время прибытия (значение в миллисекундах)
     */
    private long actualArrivalTime;

    /**
     * 【时间片】预计停靠时间（卸货的时间） 单位：ms
     */
    private long estimatedStopTime;

    /**
     * 【时间片】实际停靠时间（卸货的时间） 单位：ms
     * Расчетное время стыковки (время до разгрузки) Единица измерения: мс
     */
    private long actualStopTime;

    /**
     * 【时间片】在队列中等待的时长
     * Длительность ожидания в очереди
     */
    private long waitingTimeInQueue;

    /**
     * 是否完成了卸货，true代表完成了卸货，false代表没有
     * Завершена ли разгрузка, истинно ли разгрузка завершена, ложно ли - нет.
     */
    private boolean isUnload;

    /**
     * 罚款 单位：美元
     * Штрафы в долларах США
     */
    private int fine;

    public Freighter() {
        this.waitingTimeInQueue = 0;
        this.isUnload = false;
    }

    public Freighter(String name, TypeGoods typeGoods, int weightOrNumber, long estimatedArrivalTime,
                     long actualArrivalTime, long estimatedStopTime, long actualStopTime, long waitingTimeInQueue,
                     boolean isUnload, int fine) {
        this.name = name;
        this.typeGoods = typeGoods;
        this.weightOrNumber = weightOrNumber;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.estimatedStopTime = estimatedStopTime;
        this.actualStopTime = actualStopTime;
        this.waitingTimeInQueue = waitingTimeInQueue;
        this.isUnload = isUnload;
        this.fine = fine;
    }

    /**
     * 判断信息是否缺失，如果某个成员变量没有赋值或者异常，则返回 true；否则返回 false
     * Определяет, отсутствует ли информация, и возвращает true,
     * если переменной-члену не присвоено значение или исключение; в противном случае возвращает false
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
    public String toString() {
        return "Freighter{" +
                "name='" + name + '\'' +
                ", typeGoods=" + typeGoods +
                ", weightOrNumber=" + weightOrNumber +
                ", estimatedArrivalTime=" + estimatedArrivalTime +
                ", actualArrivalTime=" + actualArrivalTime +
                ", estimatedStopTime=" + estimatedStopTime +
                ", actualStopTime=" + actualStopTime +
                ", waitTime=" + waitingTimeInQueue +
                ", isUnload=" + isUnload +
                ", fine=" + fine +
                '}';
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

    public long getWaitingTimeInQueue() {
        return waitingTimeInQueue;
    }

    public void setWaitingTimeInQueue(long waitingTimeInQueue) {
        this.waitingTimeInQueue = waitingTimeInQueue;
    }

    public boolean isUnload() {
        return isUnload;
    }

    public void setUnload(boolean unload) {
        isUnload = unload;
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
