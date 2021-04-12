/**
 * -*- coding: utf-8 -*-
 *
 * @Time : 2021/4/12 9:52
 * @Author : NekoSilverfox
 * @FileName: JsonManager
 * @Software: IntelliJ IDEA
 * @Versions: v0.1
 * @Github ：https://github.com/NekoSilverFox
 */
package com.foxthere.service.service2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.util.Locale;
import java.util.stream.Stream;


public class JsonManager {
    public static <T> void jsonWriter(T obj, String filePath) throws IOException {
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
        mapper.writeValue(new FileWriter(filePath), obj);
    }

    public static <T> T jsonReader(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        int len = 0;
        char[] chars = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();

        while ((len = fileReader.read(chars)) != -1) {
            stringBuffer.append(new String(chars, 0, len));
        }

        System.out.println("读取到的字符串：" + stringBuffer.toString());  // TODO 测试完 注释掉

        ObjectMapper mapper = new ObjectMapper();
        T obj = mapper.readValue(stringBuffer.toString(), new TypeReference<T>() {});

        return obj;
    }
}
