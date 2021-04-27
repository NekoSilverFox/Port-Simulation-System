/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/26 13:42
 * @Author : NekoSilverfox
 * @FileName: Controller3
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.controller;

import com.foxthere.model.ConstantsTable;
import com.foxthere.model.Freighter;
import com.foxthere.model.StatisticalResults;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service3.StatisticalModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/controller3")
public class Controller3 {
    @Autowired
    RestTemplate restTemplate;

    /** http://localhost:8080/controller3/generateSimulationResults
     * 从 controller2/getFreighterTimetable 中获取一个船舶的时间表并且计算建模
     * 注意：这里不会写入到任何文件中
     * @return 返回 包含一个包含 3种 类型船的队列 和 总和 的 ArrayList 数组
     */
    @GetMapping("generateSimulationResults")
    @ResponseBody
    public ArrayList<StatisticalResults> generateSimulationResults() {

        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  先 GET 获取 FreighterTimetable 表 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
        String urlGet = "http://localhost:8080/controller2/getFreighterTimetable";

        // 请求入参
        Map<String, Long> paramMap = new HashMap<>();

        FreighterTimetable freighterTimetable = restTemplate.getForObject(urlGet, FreighterTimetable.class, paramMap);
        System.out.println("[INFO] In getForObject: " + freighterTimetable);

        ArrayList<Freighter> containerships = freighterTimetable.getContainershipList();
        int minCraneContainerships = StatisticalModels.getMinCraneNumber(containerships);
        StatisticalResults containershipsResults = StatisticalModels.getStatistics(containerships);
        containershipsResults.setNumCrane(minCraneContainerships);

        ArrayList<Freighter> bulkCarriers = freighterTimetable.getBulkCarrierList();
        int minCraneBulkCarriers = StatisticalModels.getMinCraneNumber(bulkCarriers);
        StatisticalResults bulkCarriersResults = StatisticalModels.getStatistics(bulkCarriers);
        bulkCarriersResults.setNumCrane(minCraneBulkCarriers);

        ArrayList<Freighter> tankers = freighterTimetable.getTankerList();
        int minCraneTankers = StatisticalModels.getMinCraneNumber(tankers);
        StatisticalResults tankersResults = StatisticalModels.getStatistics(tankers);
        tankersResults.setNumCrane(minCraneTankers);

        long tmpMax = Math.max(containershipsResults.getMaxUnloadingDelayTime(), bulkCarriersResults.getMaxUnloadingDelayTime());
        long maxUnloadingDelayTime = Math.max(tmpMax, tankersResults.getMaxUnloadingDelayTime());

        StatisticalResults totalResults = new StatisticalResults(
                minCraneContainerships + minCraneBulkCarriers + minCraneTankers,
                containershipsResults.getNumFreighters() + bulkCarriersResults.getNumFreighters() + tankersResults.getNumFreighters(),
                (containershipsResults.getAverageWaitingTimeInQueue() + bulkCarriersResults.getAverageWaitingTimeInQueue() + tankersResults.getAverageWaitingTimeInQueue()) / 3,
                maxUnloadingDelayTime,
                (containershipsResults.getAverageUnloadingDelayTime() + bulkCarriersResults.getAverageUnloadingDelayTime() + tankersResults.getAverageUnloadingDelayTime()) / 3,
                (containershipsResults.getAverageTimeOfUnloading() + bulkCarriersResults.getAverageTimeOfUnloading() + tankersResults.getAverageTimeOfUnloading()) / 3,
                containershipsResults.getTotalFine() + bulkCarriersResults.getTotalFine() + tankersResults.getTotalFine()
        );

        ArrayList<StatisticalResults> resultsArrayList = new ArrayList<>();
        resultsArrayList.add(containershipsResults);
        resultsArrayList.add(bulkCarriersResults);
        resultsArrayList.add(tankersResults);
        resultsArrayList.add(totalResults);


        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 处理完成，POST 到 controller2，以便其写入json文件 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
        // 声明一个请求头
        HttpHeaders httpHeaders = new HttpHeaders();

        // application/json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 远程访问的URL：
        String urlPost = "http://localhost:8080/controller2/postStatisticalResultsToJsonFile";

        /**
         * 此处使用 MultiValueMap 会报错
         *          MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
         *         paramMap.add("name", "在POST");
         *         paramMap.add("age", 14);
         *
         * 此处应该使用 HaspMap 代替，但是会有警告
         */

        // 需要使用 HTTPEntity 传参 把两个都传进去
        HttpEntity<ArrayList<StatisticalResults>> entityParam = new HttpEntity<>(resultsArrayList, httpHeaders);

        ArrayList<StatisticalResults> result = restTemplate.postForObject(urlPost, entityParam, ArrayList.class);
        return result;
//        return resultsArrayList;
    }


    @GetMapping("/showInBrowser")
    public String showInBrowser(Model model) {
        if (!(new File(ConstantsTable.RESULT_FILE_PATH).exists())) {
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        int len = 0;
        char[] charBuffer = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileReader fileReader = new FileReader(ConstantsTable.RESULT_FILE_PATH);
            while ((len = fileReader.read(charBuffer)) != -1) {
                stringBuffer.append(new String(charBuffer, 0, len));
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        String result = stringBuffer.toString();
        model.addAttribute("msg", result);

        return "info";
    }
}
