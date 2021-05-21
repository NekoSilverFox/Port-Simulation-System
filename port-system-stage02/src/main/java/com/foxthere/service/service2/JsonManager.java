/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/12 9:52
 * @Author : NekoSilverfox
 * @FileName: JsonManager
 * @Software: IntelliJ IDEA
 * @Versions: v1.0
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxthere.model.Freighter;
import com.foxthere.model.StatisticalResults;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;

@Service
public class JsonManager {
    /**
     * 将存储船舶的 ArrayList 写入到 json 文件中
     * Запись списка ArrayList хранимых судов в json-файл
     *
     * @param freighterArrayList 将存储船舶的 ArrayList
     * @param filePath 写入的路径
     * @throws IOException 文件无法创建或路径错误
     */
    public static void jsonWriter(ArrayList<Freighter> freighterArrayList, String filePath) throws IOException {
        File file = new File(filePath);

        if (filePath.isEmpty() || !filePath.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new FileNotFoundException("[ERROR] File path is empty or not a Json file");
        }else if (!file.exists()) {
            // 如果文件不存在就创建一个
            String[] split = filePath.split("\\\\");
            long count = Stream.of(split).count();

            if (count == 1) {
                file.createNewFile();
            } else {
                StringBuffer dirPath = new StringBuffer();
                for (int i = 0; i < count - 1; i++) {
                    dirPath.append(split[i]).append("\\\\");
                }

                new File(dirPath.toString()).mkdirs();
                file.createNewFile();
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileWriter(filePath), freighterArrayList);
    }


    public static void jsonStatisticalResultsWriter(ArrayList<StatisticalResults> resultsArrayList, String filePath) throws IOException {
        File file = new File(filePath);

        if (filePath.isEmpty() || !filePath.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new FileNotFoundException("[ERROR] File path is empty or not a Json file");
        }else if (!file.exists()) {
            // 如果文件不存在就创建一个
            String[] split = filePath.split("\\\\");
            long count = Stream.of(split).count();

            if (count == 1) {
                file.createNewFile();
            } else {
                StringBuffer dirPath = new StringBuffer();
                for (int i = 0; i < count - 1; i++) {
                    dirPath.append(split[i]).append("\\\\");
                }

                new File(dirPath.toString()).mkdirs();
                file.createNewFile();
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileWriter(filePath), resultsArrayList);
    }

    /**
     * 从 json 文件中，读取存储船舶的 ArrayList
     * Из файла json прочитайте список ArrayList, в котором хранится корабль
     *
     * @param filePath 读取的路径
     * @return 读取到的 ArrayList
     * @throws IOException 文件打开
     */
    public static ArrayList<Freighter> jsonReader(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        int len = 0;
        char[] chars = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();

        while ((len = fileReader.read(chars)) != -1) {
            stringBuffer.append(new String(chars, 0, len));
        }

        // System.out.println("读取到的字符串：" + stringBuffer.toString());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Freighter> freightersList = mapper.readValue(stringBuffer.toString(), new TypeReference<ArrayList<Freighter>>() {});

        return freightersList;
    }
}
