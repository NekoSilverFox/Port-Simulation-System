import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.Freighter;
import com.foxthere.service.service1.FreighterTimetable;
import com.foxthere.service.service2.JsonManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
//        write();
//        read();
        springTest();
    }

    public static void springTest() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        // 生成并写入
/*        JsonManager.jsonWriter(
                context.getBean("freighterTimetable", FreighterTimetable.class)
                        .createFreighterList(ConstantsTable.FREIGHTER_ARRIVAL_INTERVAL, ConstantsTable.DURATION_SIMULATION),
                ConstantsTable.JSON_FILE_PATH);*/

        FreighterTimetable freighterTimetable = context.getBean("freighterTimetable", FreighterTimetable.class);

        freighterTimetable.setFreighterList(JsonManager.jsonReader(ConstantsTable.JSON_FILE_PATH));

        freighterTimetable.printAllFreighterTimetable(ConstantsTable.TIME_TYPE);
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
