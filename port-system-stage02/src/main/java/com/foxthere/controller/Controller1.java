/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/26 13:42
 * @Author : NekoSilverfox
 * @FileName: Controller1
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.controller;

import com.foxthere.config.JavaConfig;
import com.foxthere.model.ConstantsTable;
import com.foxthere.model.Freighter;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/controller1")
public class Controller1 {

    /** http://localhost:8080/controller1/createFreighterTimetable
     * @return 生成一个所有船舶的时刻表
     */
    @GetMapping("/createFreighterTimetable")
    @ResponseBody
    public ArrayList<Freighter> createFreighterTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getFreighterTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        ArrayList<Freighter> freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class)
                .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION);

        try {
            JsonManager.jsonWriter(freighterTimetable, ConstantsTable.JSON_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[INFO] Successful wrote into the Json file " + ConstantsTable.JSON_FILE_PATH);
        System.out.println("[INFO] The freighterList: \n" + freighterTimetable.toString());

        return freighterTimetable;
    }


    /** http://localhost:8080/controller1/getFreighterTimetable
     * @return 返回一个所有船舶的时刻表
     */
    @GetMapping("/getFreighterTimetable")
    @ResponseBody
    public ArrayList<Freighter> getFreighterTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getFreighterTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Freighter> freighterList = freighterTimetable.getFreighterList();
        System.out.println("[INFO] The freighterList: \n" + freighterList.toString());

        return freighterList;
    }


    /** http://localhost:8080/controller1/getContainershipTimetable
     * @return 返回一个 集装箱船 的时刻表
     */
    @GetMapping("/getContainershipTimetable")
    @ResponseBody
    public ArrayList<Freighter> getContainershipTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getContainershipTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Freighter> containershipList = freighterTimetable.getContainershipList();
        System.out.println("[INFO] The containershipList: \n" + containershipList.toString());

        return containershipList;
    }


    /** http://localhost:8080/controller1/getBulkCarrierTimetable
     * @return 返回一个 液体船 的时刻表
     */
    @GetMapping("/getBulkCarrierTimetable")
    @ResponseBody
    public ArrayList<Freighter> getBulkCarrierTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getBulkCarrierTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Freighter> bulkCarrierList = freighterTimetable.getBulkCarrierList();
        System.out.println("[INFO] The bulkCarrierList: \n" + bulkCarrierList.toString());

        return bulkCarrierList;
    }

    /** http://localhost:8080/controller1/getBulkCarrierTimetable
     * @return 返回一个 散货船 的时刻表
     */
    @GetMapping("/getTankerTimetable")
    @ResponseBody
    public ArrayList<Freighter> getTankerTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getTankerTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Freighter> tankerList = freighterTimetable.getTankerList();
        System.out.println("[INFO] The tankerList: \n" + tankerList.toString());

        return tankerList;
    }
}
