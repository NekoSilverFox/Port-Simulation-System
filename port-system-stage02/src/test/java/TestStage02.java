import com.foxthere.config.JavaConfig;
import com.foxthere.model.ConstantsTable;
import com.foxthere.model.Freighter;
import com.foxthere.model.StatisticalResults;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;
import com.foxthere.service.service3.StatisticalModels;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 21:13
 * @Author : NekoSilverfox
 * @FileName: TestStage02
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
public class TestStage02 {

    public static void main(String[] args) throws IOException {
        write();
//        read();
//        springTest();
//        test();
    }

    public static void test() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        // 生成并写入
/*        JsonManager.jsonWriter(
                context.getBean("freighterTimetable", FreighterTimetable.class)
                        .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION),
                ConstantsTable.JSON_FILE_PATH);*/

        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));

        System.out.println("\n\n");

        ArrayList<Freighter> containerships = freighterTimetable.getContainershipList();
        int minCraneContainerships = StatisticalModels.getMinCraneNumber(containerships);
        StatisticalResults containershipsResults = StatisticalModels.getStatistics(containerships);
        containershipsResults.setNumCrane(minCraneContainerships);

        FreighterTimetable.printFreighterTimetable(containerships, ConstantsTable.TIME_TYPE);

        StatisticalModels.printStatistics(containershipsResults, "Results-Containerships");
        System.out.println("\n");

        System.exit(0);
    }

    public static void springTest() throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        // 生成并写入
/*        JsonManager.jsonWriter(
                context.getBean("freighterTimetable", FreighterTimetable.class)
                        .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION),
                ConstantsTable.JSON_FILE_PATH);*/

        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));

/*        Freighter freighterByTerminal = StatisticalModels.createFreighterByTerminal();
        freighterTimetable.addFreighter(freighterByTerminal);*/

//        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);

        System.out.println("\n\n");

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
        try {
            JsonManager.jsonStatisticalResultsWriter(resultsArrayList, ConstantsTable.RESULT_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }





        FreighterTimetable.printFreighterTimetable(containerships, ConstantsTable.TIME_TYPE);
        FreighterTimetable.printFreighterTimetable(bulkCarriers, ConstantsTable.TIME_TYPE);
        FreighterTimetable.printFreighterTimetable(tankers, ConstantsTable.TIME_TYPE);

        StatisticalModels.printStatistics(containershipsResults, "Results-Containerships");
        System.out.println("\n");

        StatisticalModels.printStatistics(bulkCarriersResults, "Results-BulkCarriers");
        System.out.println("\n");

        StatisticalModels.printStatistics(tankersResults, "Results-Tankers");
        System.out.println("\n");

        StatisticalModels.printStatistics(totalResults, "Results-AllFreighters");
        System.out.println("\n");
        System.exit(0);
    }

    public static void write() throws IOException {
        FreighterTimetable freighterTimetable = new FreighterTimetable();
        freighterTimetable.createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION);

        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);

        JsonManager.jsonWriter(freighterTimetable.getFreighterList(), ConstantsTable.JSON_FILE_PATH);
    }

    public static void read() throws IOException {
        ArrayList<Freighter> freighters = JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH);

        FreighterTimetable freighterTimetable = new FreighterTimetable(freighters);
        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);
    }
}
