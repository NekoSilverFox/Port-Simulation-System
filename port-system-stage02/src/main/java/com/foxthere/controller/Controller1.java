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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/controller1")
public class Controller1 {
    @Autowired
    RestTemplate restTemplate;

    /** http://localhost:8080/controller1/createFreighterTimetable
     * @return 生成一个所有船舶的时刻表，并返回；并通过 服务2 写入到 TimeTable.json 中
     */
    @GetMapping("/createFreighterTimetable")
    @ResponseBody
    public ArrayList<Freighter> createFreighterTimetable() {
        System.out.println("[INFO] Getting Freighter Timetable from the URL `http://localhost:8080/controller1/getFreighterTimetable`");

        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        ArrayList<Freighter> freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class)
                .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION);

        try {
//            File file = new File("");
//            System.out.println(file.getAbsolutePath());  // E:\apache-tomcat-9.0.45\bin
            JsonManager.jsonWriter(freighterTimetable, ConstantsTable.JSON_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[INFO] Successful wrote into the Json file " + ConstantsTable.JSON_FILE_PATH);
        System.out.println("[INFO] The freighterList: \n" + freighterTimetable.toString());

        return freighterTimetable;
    }

}
