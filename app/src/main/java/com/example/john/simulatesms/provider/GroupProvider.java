package com.example.john.simulatesms.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.MailTo;
import android.net.Uri;
import android.support.annotation.RequiresPermission;

import com.example.john.simulatesms.dao.GroupOpenHelper;

public class GroupProvider extends ContentProvider {
    private final static String AUTHORITY = "com.cn.group.provider";
    public final static Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    private static UriMatcher matcher;
    private final static int GROUP_ITEM = 0;
    private final static int GROUP_DIR = 1;

    private final static int GROUP_MAPPING_THREAD_DIR = 2;
    private final static int GROUP_MAPPING_THREAD_ITEM = 3;


    private GroupOpenHelper groupOpenHelper;
    private SQLiteDatabase db;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "group/#", GROUP_ITEM);
        matcher.addURI(AUTHORITY, "group", GROUP_DIR);

        matcher.addURI(AUTHORITY, "group_mapping_thread/#", GROUP_MAPPING_THREAD_ITEM);
        matcher.addURI(AUTHORITY, "group_mapping_thread", GROUP_MAPPING_THREAD_DIR);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowId = 0;
        switch (matcher.match(uri)) {
            case GROUP_DIR:
                rowId = db.delete("group", selection, selectionArgs);
                break;
            case GROUP_ITEM:
                String group_id = uri.getPathSegments().get(1);
                rowId = db.delete("group", "_id=?", new String[]{group_id});
                break;
            case GROUP_MAPPING_THREAD_DIR:
                rowId = db.delete("group_mapping_thread", selection, selectionArgs);
                break;
            case GROUP_MAPPING_THREAD_ITEM:
                String group_mapping_thread_id = uri.getPathSegments().get(1);
                db.delete("group_mapping_thread", "_id=?", new String[]{group_mapping_thread_id});
                break;
        }
        getContext().getContentResolver().notifyChange(BASE_URI, null);
        return rowId;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case GROUP_DIR:
                return "vnd.android.cursor.dir/vnd.com.cn.group.provider.group";
            case GROUP_ITEM:
                return "vnd.android.cursor.item/vnd.com.cn.group.provider.group";
            case GROUP_MAPPING_THREAD_DIR:
                return "vnd.android.cursor.dir/vnd.com.cn.group.provider.group_mapping_thread";
            case GROUP_MAPPING_THREAD_ITEM:
                return "vnd.android.cursor.item/vnd.com.cn.group.provider.group_mapping_thread";

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        Long rowId = 0L;
        switch (matcher.match(uri)) {
            case GROUP_DIR:
                rowId = db.insert("group", null, values);
                break;
            case GROUP_MAPPING_THREAD_DIR:
                rowId = db.insert("group_mapping_thread", null, values);
                break;
        }
        getContext().getContentResolver().notifyChange(BASE_URI, null);
        return newUri.withAppendedPath(newUri, rowId.toString());
    }

    @Override
    public boolean onCreate() {
        groupOpenHelper = GroupOpenHelper.getInstance(getContext());
        db = groupOpenHelper.getReadableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (matcher.match(uri)) {
            case GROUP_DIR:
                cursor = db.query("group", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GROUP_ITEM:
                String group_id = uri.getPathSegments().get(1);
                cursor = db.query("group", null, "_id=" + group_id, null, null, null, sortOrder);
                break;

            case GROUP_MAPPING_THREAD_ITEM:
                String group_mapping_thread_id = uri.getPathSegments().get(1);
                cursor = db.query("group_mapping_thread", null, "_id=" + group_mapping_thread_id, null, null, null, sortOrder);
                break;

            case GROUP_MAPPING_THREAD_DIR:
                cursor = db.query("group_mapping_thread", projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int rowId = 0;
        switch (matcher.match(uri)) {
            case GROUP_DIR:
                //更新所有条目
                rowId = db.update("group", values, selection, selectionArgs);
                break;
            case GROUP_ITEM:
                String group_id = uri.getPathSegments().get(1);
                rowId = db.update("group", values, "_id=?", new String[]{group_id});
                break;
            case GROUP_MAPPING_THREAD_DIR:
                rowId = db.update("group_mapping_thread", values, selection, selectionArgs);
                break;
            case GROUP_MAPPING_THREAD_ITEM:
                String group_mapping_thread_id = uri.getPathSegments().get(1);
                rowId = db.update("group_mapping_thread", values, "_id=?", new String[]{group_mapping_thread_id});
                break;
        }
        getContext().getContentResolver().notifyChange(BASE_URI, null);
        return rowId;
    }
}
