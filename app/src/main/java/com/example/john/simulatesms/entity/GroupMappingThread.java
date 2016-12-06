package com.example.john.simulatesms.entity;

import android.database.Cursor;

/**
 * Created by John on 2016/12/6.
 */

public class GroupMappingThread {
    private int _id;
    private int group_id;
    private int thread_id;

    public static GroupMappingThread createByCurosr(Cursor cursor) {
        int _id = cursor.getInt(cursor.getColumnIndex("_id"));
        int group_id = cursor.getInt(cursor.getColumnIndex("group_id"));
        int thread_id = cursor.getInt(cursor.getColumnIndex("thread_id"));
        return new GroupMappingThread(_id, group_id, thread_id);
    }

    public GroupMappingThread() {

    }

    public GroupMappingThread(int _id, int group_id, int thread_id) {
        this._id = _id;
        this.group_id = group_id;
        this.thread_id = thread_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }
}
