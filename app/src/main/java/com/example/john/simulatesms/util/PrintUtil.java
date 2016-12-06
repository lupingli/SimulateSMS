package com.example.john.simulatesms.util;

import android.database.Cursor;

import com.example.john.simulatesms.ui.activity.SMSActivity;

/**
 * Created by John on 2016/11/22.
 * 打印信息
 */

public class PrintUtil {
    /**
     * 显示cursor相关数据
     *
     * @param cursor
     */
    public static void showCursor(Cursor cursor) {
        LogUtil.d(SMSActivity.TAG, "hello");
        //System.out.print("hello");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                System.out.println(cursor.getColumnName(i) + ":--" + cursor.getString(i));
            }
            System.out.println("=========================================");
        }
    }

}
