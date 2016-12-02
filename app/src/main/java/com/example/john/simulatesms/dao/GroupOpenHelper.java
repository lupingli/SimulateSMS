package com.example.john.simulatesms.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.john.simulatesms.util.TableUtil;

/**
 * Created by John on 2016/12/2.
 */

public class GroupOpenHelper extends SQLiteOpenHelper {
    private static GroupOpenHelper instance;

    public static GroupOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GroupOpenHelper(context, "group.db", null, 1);
        }
        return instance;
    }


    public GroupOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(TableUtil.TableGroup.CREATE_TABLE);
        sqLiteDatabase.execSQL(TableUtil.TableGroupMappingThread.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i) {
            case 1:
            case 2:
                //...
                break;
        }

    }
}
