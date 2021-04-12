import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.service.service1.FreighterTimetable;

/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/11 21:13
 * @Author : NekoSilverfox
 * @FileName: Test
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
public class Test {

    public static void main(String[] args) {
        FreighterTimetable freighterTimetable = new FreighterTimetable();
        freighterTimetable.createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION);

//        FreighterTimetable.printFreighterList(freighterTimetable, ConstantsTable.TIME_TYPE);
        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);
        freighterTimetable.printContainershipTimetable(ConstantsTable.TIME_TYPE);
        freighterTimetable.printBulkCarrierTimetable(ConstantsTable.TIME_TYPE);
        freighterTimetable.printTankerTimetable(ConstantsTable.TIME_TYPE);
    }
}
