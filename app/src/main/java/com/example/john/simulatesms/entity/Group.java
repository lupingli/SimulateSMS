package com.example.john.simulatesms.entity;

import android.database.Cursor;

/**
 * Created by John on 2016/12/6.
 */

public class Group {
    private int _id;
    private String group_name;
    private long create_date;
    private int thread_count;

    public static Group createGroupByCursor(Cursor cursor) {
        Group group = null;
        int _id = cursor.getInt(cursor.getColumnIndex("_id"));
        String group_name = cursor.getString(cursor.getColumnIndex("group_name"));
        long create_date = cursor.getLong(cursor.getColumnIndex("create_date"));
        int thread_count = cursor.getInt(cursor.getColumnIndex("thread_count"));
        group = new Group(_id, group_name, create_date, thread_count);
        return group;
    }

    public Group(int _id, String group_name, long create_date, int thread_count) {
        this._id = _id;
        this.group_name = group_name;
        this.create_date = create_date;
        this.thread_count = thread_count;
    }

    public Group() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public int getThread_count() {
        return thread_count;
    }

    public void setThread_count(int thread_count) {
        this.thread_count = thread_count;
    }
}
