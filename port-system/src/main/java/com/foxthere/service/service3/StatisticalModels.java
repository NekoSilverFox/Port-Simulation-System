/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/12 18:04
 * @Author : NekoSilverfox
 * @FileName: StatisticalModels
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service3;

import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.Freighter;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatisticalModels {
    public static void main(String[] args) throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        // 生成并写入
/*        JsonManager.jsonWriter(
                context.getBean("freighterTimetable", FreighterTimetable.class)
                        .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION),
                ConstantsTable.JSON_FILE_PATH);*/

        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));

        //freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);


        // 线程的数量（某种起重机的数量）
        int numThread = 1;

        // 传入一个测试的模型
        ArrayList<Freighter> containershipList = freighterTimetable.getContainershipList();
        FreighterTimetable.printFreighterTimetable(containershipList, ConstantsTable.TIME_TYPE);

        // 当前的总罚金
        int currentTotalFines = ConstantsTable.CRANE_PRICE * numThread;

        // 上一个
        int lastTotalFines = 0;
        while (lastTotalFines < currentTotalFines) {
            lastTotalFines = currentTotalFines;

            currentTotalFines = ConstantsTable.CRANE_PRICE * numThread;
            ExecutorService executorService = Executors.newFixedThreadPool(numThread);

            for (int i = 0; i < containershipList.size(); i++) {
                // 卸货完成的时间 - 下一艘船的到达时间就是下一艘船的等待时间
                long waitingTime = 0;
                if ((i + numThread) < containershipList.size()) {
                    waitingTime = (containershipList.get(i).getActualStopTime() + containershipList.get(i).getEstimatedArrivalTime())
                            - containershipList.get(i + numThread).getActualArrivalTime()
                            + containershipList.get(i).getWaitingTimeInQueue();
                }

                int nextShipOnThisThread = i + numThread;
                int index = i;

                long finalWaitingTime = waitingTime;
                executorService.submit(() -> {
                    if (finalWaitingTime > 0 && (containershipList.size() > nextShipOnThisThread)) {
                        containershipList.get(nextShipOnThisThread).setWaitingTimeInQueue(finalWaitingTime);
                        // 原来就已经有的罚金（因为起重机的超时工作）
                        int fineAlreadyHave = containershipList.get(index).getFine();
                        containershipList.get(nextShipOnThisThread).setFine((int) (finalWaitingTime / 1000 / 60 / 60 * 100 + fineAlreadyHave));
                    }

                });
            }

            // 统计总罚款
            for (Freighter freighter : containershipList) {
                currentTotalFines += freighter.getFine();
            }

            numThread++;
        }
        System.out.println("最优起重机数量：" + (numThread - 1));
        System.out.println("总罚金 + 起重机数量：" + lastTotalFines);
    }
}
