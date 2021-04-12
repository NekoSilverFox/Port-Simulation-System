/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 15:25
 * @Author : NekoSilverfox
 * @FileName: Crane
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.pojo.defines;

public class Crane {
    /** 起重机可以处理的货物类型 */
    private TypeGoods typeCrane;

    /** 起重机的效率：每处理一件或者一吨所需的时间（单位：ms） */
    private long workpieceRatio;

    /** 每台起重机的价格（默认 30000 美金） */
    private int price;

    public Crane() {
        this.price = ConstantsTable.CRANE_PRICE;
    }

    public Crane(TypeGoods typeCrane, int workpieceRatio, int price) {
        this.typeCrane = typeCrane;
        this.workpieceRatio = workpieceRatio;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Crane{" +
                "typeCrane=" + typeCrane +
                ", workpieceRatio=" + workpieceRatio +
                ", price=" + price +
                '}';
    }

    public TypeGoods getTypeCrane() {
        return typeCrane;
    }

    public void setTypeCrane(TypeGoods typeCrane) {
        this.typeCrane = typeCrane;
    }

    public long getWorkpieceRatio() {
        return workpieceRatio;
    }

    public void setWorkpieceRatio(long workpieceRatio) {
        this.workpieceRatio = workpieceRatio;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
