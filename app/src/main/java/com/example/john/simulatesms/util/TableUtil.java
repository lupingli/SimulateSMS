package com.example.john.simulatesms.util;

/**
 * Created by John on 2016/12/2.
 */

public class TableUtil {
    /**
     * 创建group表
     * _id,group_name,create_date,thread_count
     */
    public interface TableGroup {
        String CREATE_TABLE = "create table groups" +
                " (_id integer primary key autoincrement ," +
                " group_name varchar , " +
                "create_date integer," +
                "thread_count integer)";

        //字段
        String _ID_FIELD = "_id";
        String GROUP_NAME_FIELD = "group_name";
        String CREATE_DATE_FIELD = "create_date";
        String THREAD_COUNT_FIELD = "thread_count";

    }

    /**
     * 创建group_mapping_thread
     * 与Thread进行映射
     */
    public interface TableGroupMappingThread {
        String CREATE_TABLE = "create table group_mapping_thread " +
                "(_id integer primary key autoincrement , " +
                "group_id integer , " +
                "thread_id  integer)";

        //字段
        String _ID_FIELD = "_id";
        String GROUP_ID_FIELD = "group_id";
        String THREAD_ID_FIELD = "thread_id";

    }
}
