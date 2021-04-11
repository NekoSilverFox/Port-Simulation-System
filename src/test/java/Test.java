import com.foxthere.pojo.defines.ConstantsTable;
import com.foxthere.pojo.defines.TypeGoods;

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
        for (int i = 0; i < 100000; i++) {
            int num = new Random().nextInt(ConstantsTable.MAX_TEU_TRANSPORTED - ConstantsTable.MIN_TEU_TRANSPORTED + 1) + ConstantsTable.MIN_TEU_TRANSPORTED;
            System.out.println(num);
        }
    }
}
