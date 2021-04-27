/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/27 8:37
 * @Author : NekoSilverfox
 * @FileName: Controller4
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxthere.model.ConstantsTable;
import com.foxthere.model.StatisticalResults;
import com.foxthere.service.service3.StatisticalModels;
import com.foxthere.unit.InvokeWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/controller4")
public class Controller4 {
    @Autowired
    RestTemplate restTemplate;

    /** http://localhost:8080/controller4/startSimulation
     *
     */
    @GetMapping("/startSimulation")
    public String startSimulation(Model model) throws InterruptedException {
        InvokeWeb.invoke("http://localhost:8080/controller1/createFreighterTimetable");
        TimeUnit.MILLISECONDS.sleep(1000);

        InvokeWeb.invoke("http://localhost:8080/controller2/getFreighterTimetable");
        TimeUnit.MILLISECONDS.sleep(1000);

        InvokeWeb.invoke("http://localhost:8080/controller3/generateSimulationResults");
        TimeUnit.MILLISECONDS.sleep(1000);

        InvokeWeb.invoke("http://localhost:8080/controller3/showInBrowser");

        model.addAttribute("msg", "Running");
        return "SimpleMsg";
    }

    /** http://localhost:8080/controller4/startSimulation/{jsonFileName}
     *
     * @param jsonFileName
     * @param model
     * @throws InterruptedException
     */
    @RequestMapping("/startSimulation")
//    @GetMapping("/startSimulation/{jsonFileName}")
    public String startSimulation(@RequestParam("s") String jsonFileName, Model model) throws InterruptedException {
        synchronized (Controller4.class) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InvokeWeb.invoke("http://localhost:8080/controller1/createFreighterTimetable");
                        Thread.sleep(10000);
//                        TimeUnit.MILLISECONDS.sleep(1000);

                        InvokeWeb.invoke("http://localhost:8080/controller2/getFreighterTimetableByPath/" + jsonFileName);
                        TimeUnit.MILLISECONDS.sleep(1000);

                        InvokeWeb.invoke("http://localhost:8080/controller3/generateSimulationResults/"  + jsonFileName);
                        TimeUnit.SECONDS.sleep(10);

                        InvokeWeb.invoke("http://localhost:8080/controller3/showInBrowser");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }).run();
        }


        model.addAttribute("msg", "Running");
        return "SimpleMsg";
    }
}
