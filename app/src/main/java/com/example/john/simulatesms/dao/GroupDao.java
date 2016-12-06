package com.example.john.simulatesms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.john.simulatesms.entity.Conversation;
import com.example.john.simulatesms.entity.Group;
import com.example.john.simulatesms.ui.activity.SMSActivity;
import com.example.john.simulatesms.util.ConstantUtil;
import com.example.john.simulatesms.util.LogUtil;
import com.example.john.simulatesms.util.TableUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2016/12/6.
 */

public class GroupDao {
    public static void insert(ContentResolver contentResolver, Group group) {
        ContentValues values = new ContentValues();
        values.put("group_name", group.getGroup_name());
        values.put("create_date", group.getCreate_date());
        values.put("thread_count", group.getThread_count());
        contentResolver.insert(ConstantUtil.URI.GROUP_URI, values);
    }

    public static void update(ContentResolver contentResolver, Group group) {
        ContentValues values = new ContentValues();
        values.put("group_name", group.getGroup_name());
        values.put("thread_count", group.getThread_count());
        Uri uri = Uri.withAppendedPath(ConstantUtil.URI.GROUP_URI, group.get_id() + "");
        contentResolver.update(uri, values, null, null);
    }

    public static void delete(ContentResolver contentResolver, Group group) {
        Uri uri = Uri.withAppendedPath(ConstantUtil.URI.GROUP_URI, group.get_id() + "");
        contentResolver.delete(uri, null, null);
    }

    public static boolean hasGroup(ContentResolver contentResolver, String name) {
        boolean flag = false;
        Cursor cursor = contentResolver.query(ConstantUtil.URI.GROUP_URI, null, "group_name = ?", new String[]{name}, null, null);
        if (cursor.moveToFirst()) {
            flag = true;
        }
        return flag;
    }

    public static boolean hasData(ContentResolver contentResolver) {
        boolean flag = false;
        Cursor cursor = contentResolver.query(ConstantUtil.URI.GROUP_URI, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            flag = true;
        }
        return flag;
    }

    public static String getNameById(ContentResolver contentResolver, int _id) {
        LogUtil.d(SMSActivity.TAG, "_id==" + _id);
        String name = null;
        Cursor cursor = contentResolver.query(Uri.withAppendedPath(ConstantUtil.URI.GROUP_URI, _id + ""), null, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = Group.createGroupByCursor(cursor).getGroup_name();
        }
        return name;
    }

    public static String[] getAllGroup(ContentResolver contentResolver) {
        List<String> names = new ArrayList<String>();
        Cursor cursor = contentResolver.query(ConstantUtil.URI.GROUP_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            names.add(Group.createGroupByCursor(cursor).getGroup_name());
        }
        //list转换为数组
        String[] array = new String[names.size()];
        return names.toArray(array);
    }

    public static Group getGroupById(ContentResolver contentResolver, int groupId) {
        Group group = null;
        Uri uri = Uri.withAppendedPath(ConstantUtil.URI.GROUP_URI, groupId + "");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            group = Group.createGroupByCursor(cursor);
        }
        return group;
    }
}
