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
import java.util.concurrent.TimeUnit;

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
        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 变量区 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
        // 线程的数量（某种起重机的数量）
        int numThreadOrCrane = ConstantsTable.CRANE_INITIAL_NUM;

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(numThreadOrCrane);

        // 当前情况的总金额（总罚金 + 起重机的钱）
        long currentTotalCost = 0;

        // 上一个情况的总金额（总罚金 + 起重机的钱）
        long lastTotalCost = ConstantsTable.CRANE_PRICE * numThreadOrCrane;

        // 当前队列
        ArrayList<Freighter> currentFreighters = null;

        // 上次的队列
        ArrayList<Freighter> lastFreighters = null;

        // 锁对象
        Object lockObj = new Object();

        // 使用 while 循环迭代尝试
        while (true) {

            // 首先将船舶的队伍进行备份，避免在处理计算时影响原队列出错
            currentFreighters = freighters;


                /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  numThreadOrCrane的情况处理开始，计算必要的信息 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
                for (int i = 0; i < currentFreighters.size(); i++) {

            synchronized (lockObj) {
                    ArrayList<Freighter> finalCurrentFreighters = currentFreighters;
                    int finalI = i;

                    executorService.submit(() -> {

                        int currentThreadID = (int) Thread.currentThread().getId();

                        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  计算是否使用 2 台起重机 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
                         * 如果数组不越界，并且 【当前船使用两台起重机的处理完成时刻(点) < 下一艘船的到达时刻(点)】，那么给他分配两台起重机（也就是需要把原本的卸货时间除以2）
                         * 其中 【当前船使用两台起重机的处理完成时刻(点) == 当前船的实际到达时间(片) + 因为前面船的延误时间(片) + 使用两台起重机的处理完成时间(片)】
                         * unloadingTimeIfUse2Crane -> 如果使用两台起重机的话，卸货结束时刻(点)
                         */
                        long unloadingTimeIfUse2Crane = finalCurrentFreighters.get(finalI).getActualArrivalTime()
                                + finalCurrentFreighters.get(finalI).getActualStopTime()
                                + (finalCurrentFreighters.get(finalI).getEstimatedStopTime() / 2);
                        if (
                                ((finalI + currentThreadID) < finalCurrentFreighters.size())
                                        && (unloadingTimeIfUse2Crane > finalCurrentFreighters.get(finalI + currentThreadID).getActualArrivalTime())
                        ) {
                            // 将理论卸货时间折半
                            finalCurrentFreighters.get(finalI).setEstimatedStopTime(
                                    finalCurrentFreighters.get(finalI).getEstimatedStopTime() / 2
                            );

                            // 将实际卸货时间折半
                            finalCurrentFreighters.get(finalI).setActualStopTime(
                                    finalCurrentFreighters.get(finalI).getActualStopTime() / 2
                            );

                            // 重新计算罚金并赋值
                            finalCurrentFreighters.get(finalI).setFine(
                                    (int) ((finalCurrentFreighters.get(finalI).getActualStopTime() - finalCurrentFreighters.get(finalI).getEstimatedStopTime())
                                            / 1000 / 60 / 60 * ConstantsTable.FINE_EVERY_HOUR)
                            );
                        }

                        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 计算船舶的等待时间 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
                         * 本艘船的等待时间 = (上艘船的实际卸货时间 - 理论卸货时间) + 原本在队列中的等待时间
                         */
                        // 本艘船的等待时间
                        long currentFreighterWaitingTime = 0;
                        if ((finalI + currentThreadID) < finalCurrentFreighters.size()) {
                            currentFreighterWaitingTime =
                                    (finalCurrentFreighters.get(finalI).getActualStopTime() - finalCurrentFreighters.get(finalI).getEstimatedStopTime())
                                            + finalCurrentFreighters.get(finalI + currentThreadID).getWaitingTimeInQueue();

                            // 设置本线程要处理的下一艘的等待时间（由上面计算得到）
                            finalCurrentFreighters.get(finalI + currentThreadID).setWaitingTimeInQueue(currentFreighterWaitingTime);
                        }


                        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 计算船舶的罚金 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
                         * 我觉得这块不太对！！
                         */
                        if (
                                (currentFreighterWaitingTime > 0)
                                        && ((finalI + currentThreadID) > finalCurrentFreighters.size())
                        ) {
                            // 本线程下艘要处理的船原来就已经有的罚金（因为起重机的超时工作）
                            int fineAlreadyHaveNextFreighters = finalCurrentFreighters.get(finalI).getFine();

                            // 为了便于计算，把等待的时间（毫秒值）转化为小时
                            int finalWaitingHoursNextFreighters = (int) (currentFreighterWaitingTime / 1000 / 60 / 60);

                            // 【最终罚金】 == 原罚金 + 加上因为在队列中等待而产生的新罚金
                            int totalFineNextFreighters = fineAlreadyHaveNextFreighters
                                    + finalWaitingHoursNextFreighters * ConstantsTable.FINE_EVERY_HOUR;

                            finalCurrentFreighters.get(finalI + currentThreadID).setFine(totalFineNextFreighters);
                        }

                        /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 把当前船的状态设置为 “已卸货” ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
                        finalCurrentFreighters.get(finalI).setUnload(true);

                    });  // 进程快结束

                    /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 让线程睡眠一会 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
                    try {
                        System.out.println("Number crane: " + numThreadOrCrane + " Processing：No." + i);
                        lockObj.wait(ConstantsTable.DEFAULT_SLEEP_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } // for 循环结束

            } // 同步块结束



            /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  判断是否需要进行下次迭代 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
            // 计算当起重机数量为 numThreadOrCrane 时（当前情况），总的消费为多少
            for (Freighter freighter : currentFreighters) {
                // 注意：当 for 循环执行完毕时，【currentTotalCost == 各个船只的罚金 + 起重机的数量 * 起重机的个数】
                currentTotalCost += freighter.getFine();
            }

            // 当 【当前情况的总金额 > 上一个情况的总金额】 时，那么上一个情况便为最优解，也就是花费最小的情况，此时应当终止程序并将参数中的集合给覆盖
            if ((lastFreighters != null) && (currentTotalCost > lastTotalCost)) {
                // 注意，要用这种方式替换（下面两行），否则会可能导致数组大小异常！
//                freighters.clear();
//                freighters.addAll(lastFreighters);
                freighters = lastFreighters;

                // 将上一情况的起重机数量返回（最优情况）
                return (numThreadOrCrane - 1);
            }


            /** ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  不是最优解，所以为下次迭代做准备（current 变 last） ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ */
            // 当起重机数量为 numThreadOrCrane 的情况计算完毕，将计算完的队列进行备份
            lastFreighters = currentFreighters;

            // 当前迭代完毕，将当前状况的总金额赋值到上一个情况（也就是说开始新情况了，现在的current情况 变成了 last情况）
            lastTotalCost = currentTotalCost;

            // 初始化金额（只有起重机的总花费）
            currentTotalCost = (long) ConstantsTable.CRANE_PRICE * numThreadOrCrane;

            // 当起重机数量为 numThreadOrCrane 的情况计算完毕，进行 +1 操作。准备进行下一种情况的计算
            numThreadOrCrane++;

            // 重置线程池
            executorService.shutdownNow();
            executorService = Executors.newFixedThreadPool(numThreadOrCrane);
        }
    }


    /**
     * 计算 服务三 中的统计结果（注意：该方法不会对起重机数量进行赋值）
     *
     * @param freighters 需要计算的货船队列
     * @return 服务三中需求的结果
     */
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
            averageTimeOfUnloading += freighter.getWaitingTimeInQueue();
            totalFine += freighter.getFine();

        }

        averageWaitingTimeInQueue = averageWaitingTimeInQueue / numFreighters;
        averageUnloadingDelayTime = averageUnloadingDelayTime / numFreighters;
        averageTimeOfUnloading = averageTimeOfUnloading / numFreighters;

        return new StatisticalResults(0, numFreighters, averageWaitingTimeInQueue, maxUnloadingDelayTime,
                averageUnloadingDelayTime, averageTimeOfUnloading, totalFine);
    }


    /**
     * 按照格式打印结果表格
     *
     * @param results     要打印的结果
     * @param tableHeader 表头的字符串（说明信息）
     */
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
                + String.format("%-35s", results.getAverageWaitingTimeInQueue() / 1000 / 60 + " min")
                + String.format("%-35s", results.getMaxUnloadingDelayTime() / 1000 / 60 + " min")
                + String.format("%-35s", results.getAverageUnloadingDelayTime() / 1000 / 60 + " min")
                + String.format("%-35s", results.getAverageTimeOfUnloading() / 1000 / 60 + " min")
                + String.format("%-25s", "$" + results.getTotalFine())
        );

        System.out.println(ConstantsTable.TABLE_LINE);
    }

}
