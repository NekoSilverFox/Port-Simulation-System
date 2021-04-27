/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/26 13:42
 * @Author : NekoSilverfox
 * @FileName: Controller2
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.controller;

import com.foxthere.config.JavaConfig;
import com.foxthere.model.ConstantsTable;
import com.foxthere.model.Freighter;
import com.foxthere.model.StatisticalResults;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;
import com.foxthere.unit.ErrorJump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 用于从 Json 文件中获取船舶的时间表：<br/>
 *
 *  - 按照默认路径获取<b><i><big>所有船舶</big></i></b>的时间表：<br/>
 *      http://localhost:8080/controller2/getFreighterTimetable<br/>
 *<br/>
 *  - 按照默认路径获取<b><i><big>集装箱货船</big></i></b>的时间表：<br/>
 *      http://localhost:8080/controller2/getContainershipTimetable<br/>
 *<br/>
 *  - 按照默认路径获取<b><i><big>液货船</big></i></b>的时间表：<br/>
 *      http://localhost:8080/controller2/getBulkCarrierTimetable<br/>
 *<br/>
 *  - 按照默认路径获取<b><i><big>散货船</big></i></b>的时间表：<br/>
 *      http://localhost:8080/controller2/getBulkCarrierTimetable<br/>
 *<br/>
 * <hr>
 *<br/>
 *  - 按照文件路径获取<b><i><big>所有船舶</big></i></b>的时间表：<br/>
 *      http://localhost:8080/controller2/getFreighterTimetableByPath/{jsonFileName}<br/>
 */

@Controller
@RequestMapping("/controller2")
public class Controller2 {
    @Autowired
    RestTemplate restTemplate;

    /** http://localhost:8080/controller2/getFreighterTimetable
     * @return 按照Json文件的默认路径，返回一个所有船舶的时刻表
     */
    @GetMapping("/getFreighterTimetable")
    @ResponseBody
    public FreighterTimetable getFreighterTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller2/getFreighterTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ArrayList<Freighter> freighterList = freighterTimetable.getFreighterList();
//        System.out.println("[INFO] The freighterList: \n" + freighterList.toString());

        return freighterTimetable;
    }


    /** http://localhost:8080/controller2/getFreighterTimetableByPath
     * @return 按照用户给定Json文件路径，返回一个所有船舶的时刻表
     */
    @GetMapping("/getFreighterTimetableByPath/{jsonFileName}")
    @ResponseBody
    public FreighterTimetable getFreighterTimetableByPath(@PathVariable String jsonFileName) throws IOException {
        System.out.println("[INFO] Getting Freighter Timetable by path from the URL `http://localhost:8080/controller2/getFreighterTimetableByPath/" + jsonFileName + "`");

        if (jsonFileName.isEmpty() || !jsonFileName.toLowerCase().endsWith(".json")) {
            System.out.println("[ERROR] " + jsonFileName + " file is empty or is not a Json file");
            ErrorJump.throwError();
            return null;
        }

        String jsonFilePath = ConstantsTable.JSON_FILE_SAVE_PATH + jsonFileName;
        System.out.println("[INFO] Json file path `" + jsonFilePath + "`");

        if (!(new File(jsonFilePath).exists())) {
            System.out.println("[ERROR] File does not exist " + jsonFilePath);
            ErrorJump.throwError();
            return null;
        }

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        try {
            freighterTimetable.setFreighterList(JsonManager.jsonReader(jsonFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ArrayList<Freighter> freighterList = freighterTimetable.getFreighterList();
//        System.out.println("[INFO] The freighterList: \n" + freighterList.toString());

        return freighterTimetable;
    }

    /** http://localhost:8080/controller2/getContainershipTimetable
     * @return 按照Json文件的默认路径，返回一个 集装箱船 的时刻表
     */
    @GetMapping("/getContainershipTimetable")
    @ResponseBody
    public ArrayList<Freighter> getContainershipTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller2/getContainershipTimetable`");

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


    /** http://localhost:8080/controller2/getBulkCarrierTimetable
     * @return 按照Json文件的默认路径，返回一个 液体船 的时刻表
     */
    @GetMapping("/getBulkCarrierTimetable")
    @ResponseBody
    public ArrayList<Freighter> getBulkCarrierTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller2/getBulkCarrierTimetable`");

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


    /** http://localhost:8080/controller2/getBulkCarrierTimetable
     * @return 按照Json文件的默认路径，返回一个 散货船 的时刻表
     */
    @GetMapping("/getTankerTimetable")
    @ResponseBody
    public ArrayList<Freighter> getTankerTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller2/getTankerTimetable`");

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



    /** 【通过POST】http://localhost:8080/controller2/postStatisticalResultsToJsonFile
     * 将 post 过来的数据保存在json文件中
     * @param resultsArrayList 要保存的数据
     * @return
     */
    @PostMapping("postStatisticalResultsToJsonFile")
//    @RequestMapping("/postStatisticalResultsToJsonFile")
    @ResponseBody
    public ArrayList<StatisticalResults> postStatisticalResultsToJsonFile(@RequestBody ArrayList<StatisticalResults> resultsArrayList) {

        try {
            JsonManager.jsonStatisticalResultsWriter(resultsArrayList, ConstantsTable.RESULT_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------- postStatisticalResultsToJsonFile ----------------------------");
        System.out.println(resultsArrayList);

        return resultsArrayList;
    }
}
