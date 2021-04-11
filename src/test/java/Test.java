import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.Freighter;
import com.foxthere.pojo.defines.TypeGoods;
import com.foxthere.service.service1.FreighterTimetable;

import java.util.Locale;
import java.util.Random;

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
        freighterTimetable.printFreighterList(ConstantsTable.TIME_TYPE);
    }
}
