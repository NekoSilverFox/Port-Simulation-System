import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.Freighter;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;

import javax.imageio.plugins.tiff.FaxTIFFTagSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    public static void main(String[] args) throws IOException {
//        FreighterTimetable freighterTimetable = new FreighterTimetable();
//        freighterTimetable.createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION);

//        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);

//        JsonManager.jsonWriter(freighterTimetable.getFreighterList(), ConstantsTable.JSON_FILE_PATH);
        ArrayList<Freighter> freightersList = JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH);

        for (Freighter freighter : freightersList) {
            System.out.println(freighter);
        }
    }
}
