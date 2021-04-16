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
import com.foxthere.pojo.defines.StatisticalResults;
import com.foxthere.pojo.defines.TypeGoods;
import com.foxthere.service.service1.InfoGenerator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatisticalModels {

    public static Freighter createFreighterByTerminal() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Freighter name: ");
        String name = scanner.next();

        TypeGoods typeGoods;
        while (true) {
            System.out.print("Type goods: [1]" + TypeGoods.CONTAINER + " [2]" + TypeGoods.BULK_CARGO + " [3]" + TypeGoods.LIQUID + " ");
            int choose = scanner.nextInt();
            if (choose == 1) {
                typeGoods = TypeGoods.CONTAINER;
                break;
            } else if (choose == 2) {
                typeGoods = TypeGoods.BULK_CARGO;
                break;
            } else if (choose == 3) {
                typeGoods = TypeGoods.LIQUID;
                break;
            } else {
                System.out.println("[INFO] Unknown choose, please again");
            }
        }

        System.out.println("Weight or number goods: ");
        int weightOrNumber = scanner.nextInt();

        System.out.println("Estimated arrival time (ms) [#Default now]: ");
        long estimatedArrivalTime = scanner.nextLong();
        if (estimatedArrivalTime == 0) {
            estimatedArrivalTime = System.currentTimeMillis();
        }

        System.out.println("Estimated stop time (ms) [#Default 4 h]: ");
        long estimatedStopTime = scanner.nextLong();
        if (estimatedStopTime == 0) {
            estimatedStopTime += 4 * 60 * 60 * 1000;
        }

        System.out.println("Is already unload (true or false): ");
        boolean isUnload = scanner.nextBoolean();

        Freighter freighter = new Freighter(name, typeGoods, weightOrNumber,
                estimatedArrivalTime, InfoGenerator.randomActualArrivalTime(estimatedArrivalTime),
                estimatedStopTime, InfoGenerator.randomActualUnloadingTime(estimatedStopTime),
                0, isUnload, 0);

        return freighter;
    }

    public static int getMinCraneNumber(ArrayList<Freighter> freighters) {
        // 线程的数量（某种起重机的数量）
        int numThread = ConstantsTable.CRANE_INITIAL_NUM;

        // 当前的总罚金 + 起重机的钱
        int currentTotalCost = ConstantsTable.CRANE_PRICE * numThread;

        // 上一个
        int lastTotalFines = 0;

        while (lastTotalFines < currentTotalCost) {
            lastTotalFines = currentTotalCost;  // TODO 写反了？

            currentTotalCost = ConstantsTable.CRANE_PRICE * numThread;
            ExecutorService executorService = Executors.newFixedThreadPool(numThread);
            Object lockObj = new Object();
            System.out.println(Thread.currentThread().getId());
//            synchronized (StatisticalModels.class) {
            synchronized (lockObj) {
            for (int i = 0; i < freighters.size(); i++) {
                // 本艘船的等待时间 ==  (上艘船的实际卸货时间 - 理论卸货时间) + 原本等待时间 |||||||||||||||卸货完成的时间 - 下一艘船的到达时间就是下一艘船的等待时间
                long waitingTime = 0;
                if ((i + numThread) < freighters.size()) {  // TODO 【错误】改为线程的编号 Thread.currentThread().getId()
                    waitingTime = (freighters.get(i).getActualStopTime() + freighters.get(i).getEstimatedArrivalTime())  // TODO 改函数名
                            - freighters.get(i - numThread).getActualArrivalTime() // 原来是 +
                            + freighters.get(i).getWaitingTimeInQueue();
                }

                int nextShipOnThisThread = i + numThread;
                int index = i;

                long finalWaitingMS = waitingTime;
// 原来同步块的位置在这下面
                    executorService.submit(() -> {
                        // 算罚金
                        if ((finalWaitingMS > 0) && (freighters.size() > nextShipOnThisThread)) {
                            freighters.get(nextShipOnThisThread).setWaitingTimeInQueue(finalWaitingMS);

                            // 原来就已经有的罚金（因为起重机的超时工作）
                            int fineAlreadyHave = freighters.get(index).getFine();
                            int finalWaitingHours = (int) (finalWaitingMS / 1000 / 60 / 60);
                            freighters.get(nextShipOnThisThread).setWaitingTimeInQueue(finalWaitingMS);
                            freighters.get(nextShipOnThisThread).setFine((int) (finalWaitingHours * ConstantsTable.FINE_EVERY_HOUR/* + fineAlreadyHave*/));
                        }
                        // wait 代码块未执行
                        try {
//                            Thread.sleep(ConstantsTable.DEFAULT_SLEEP_MS);
                            lockObj.wait(ConstantsTable.DEFAULT_SLEEP_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                freighters.get(i).setUnload(true);
            }  // end synchronized

            }  // end for

            // 统计总罚款 + 起重机金额
            for (Freighter freighter : freighters) {
                currentTotalCost += freighter.getFine();
            }

            executorService.shutdownNow();

            // 测试当下一个数量线程时的情况
            numThread++;
        }

        return (numThread - 1);
    }


    public static StatisticalResults getStatistics(ArrayList<Freighter> freighters) {
        if ((freighters == null) || (freighters.isEmpty())) {
            return null;
        }

        // 卸货船只的数量
        int numFreighters = freighters.size();

        // 在队伍中平均等待时长（和最大时长 同下） (ms)
        long averageWaitingTimeInQueue = 0;

        // 最大的卸货等待时间 (ms)
        long maxUnloadingDelayTime = freighters.get(0).getWaitingTimeInQueue();

        // 平均的卸货延迟时间 (ms)
        long averageUnloadingDelayTime = 0;

        // 卸货的平均时长 (ms)
        long averageTimeOfUnloading = 0;

        // 总罚款
        long totalFine = 0;

        for (Freighter freighter : freighters) {
            averageWaitingTimeInQueue += freighter.getWaitingTimeInQueue();

            if (freighter.getWaitingTimeInQueue() > maxUnloadingDelayTime) {
                maxUnloadingDelayTime = freighter.getWaitingTimeInQueue();
            }

            averageUnloadingDelayTime += (freighter.getActualStopTime() - freighter.getEstimatedStopTime());
            averageTimeOfUnloading += freighter.getActualStopTime();
            totalFine += freighter.getFine();

        }

        averageWaitingTimeInQueue = averageWaitingTimeInQueue / numFreighters;
        averageUnloadingDelayTime = averageUnloadingDelayTime / numFreighters;
        averageTimeOfUnloading = averageTimeOfUnloading / numFreighters;

        return new StatisticalResults(0, numFreighters, averageWaitingTimeInQueue, maxUnloadingDelayTime,
                averageUnloadingDelayTime, averageTimeOfUnloading, totalFine);
    }

    public static void printStatistics(StatisticalResults results, String tableHeader) {
        if (results == null) {
            throw new NullPointerException("[ERROR] Object StatisticalResults can not be null");
        }
        System.out.println(tableHeader + " " + ConstantsTable.TABLE_LINE);

        System.out.println(String.format("%-20s", "Min cranes")
                        + String.format("%-25s", "Number freighters")
                        + String.format("%-35s", "Average waiting time in queue")
                        + String.format("%-35s", "Max unloading delay time")
                        + String.format("%-35s", "Average unloading delay time")
                        + String.format("%-35s", "Average time of unloading")
                        + String.format("%-25s", "Total fine")
        );

        System.out.println(ConstantsTable.TABLE_LINE);

        System.out.println(String.format("%-20s", results.getNumCrane())
                        + String.format("%-25s", results.getNumFreighters())
                        + String.format("%-35s", results.getAverageWaitingTimeInQueue() / 1000 / 60 / 60 + " min")
                        + String.format("%-35s", results.getMaxUnloadingDelayTime() / 1000 / 60 / 60 + " min")
                        + String.format("%-35s", results.getAverageUnloadingDelayTime() / 1000 / 60 / 60 + " min")
                        + String.format("%-35s", results.getAverageTimeOfUnloading() / 1000 / 60 / 60 + " min")
                        + String.format("%-25s", "$" + results.getTotalFine())
        );

        System.out.println(ConstantsTable.TABLE_LINE);
    }

}
