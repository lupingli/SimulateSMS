package com.example.john.simulatesms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.john.simulatesms.entity.GroupMappingThread;
import com.example.john.simulatesms.ui.activity.SMSActivity;
import com.example.john.simulatesms.util.ConstantUtil;
import com.example.john.simulatesms.util.LogUtil;

/**
 * Created by John on 2016/12/6.
 */

public class GroupMappingThreadDao {
    public static String getGroupNameByThreadId(ContentResolver contentResolver, int threadId) {
        String name = null;
        Cursor cursor = contentResolver.query(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, null, "thread_id=?", new String[]{threadId + ""}, null);
        if (cursor.moveToFirst()) {
            GroupMappingThread groupMappingThread = GroupMappingThread.createByCurosr(cursor);
            LogUtil.d(SMSActivity.TAG, "group_id==" + groupMappingThread.getGroup_id());
            name = GroupDao.getNameById(contentResolver, groupMappingThread.getGroup_id());
        }
        return name;
    }

    public static void insert(ContentResolver contentResolver, GroupMappingThread groupMappingThread) {
        ContentValues values = new ContentValues();
        values.put("group_id", groupMappingThread.getGroup_id());
        values.put("thread_id", groupMappingThread.getThread_id());
        contentResolver.insert(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, values);
    }

    public static void deleteByThreadId(ContentResolver contentResolver, int thread_id) {
        contentResolver.delete(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, "thread_id=?", new String[]{thread_id + ""});
    }

    public static void deleteByGroupId(ContentResolver contentResolver, int groupId) {
        contentResolver.delete(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, "group_id=?", new String[]{groupId + ""});
    }

    public static int getGroupIdByThreadId(ContentResolver contnContentResolver, int thread_id) {
        int group_id = 0;
        Cursor cursor = contnContentResolver.query(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, null, "thread_id=?", new String[]{thread_id + ""}, null);
        if (cursor.moveToFirst()) {
            group_id = GroupMappingThread.createByCurosr(cursor).getGroup_id();
        }
        return group_id;
    }
}
