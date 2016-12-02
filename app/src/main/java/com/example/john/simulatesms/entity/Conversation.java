package com.example.john.simulatesms.entity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.app.SimulateSMSApplication;
import com.example.john.simulatesms.dao.ContactDao;

/**
 * Created by John on 2016/11/25.
 */

public class Conversation {
    private String _id;
    private String thread_id;
    private String snippet;
    private String date;
    private String address;
    private String name;
    private String msg_count;
    private Bitmap avatar;


    /**
     * 根据Cursor去创建Conversation对象
     *
     * @param cursor
     * @return
     */
    public static Conversation createFromCursor(Cursor cursor) {
        Context context = SimulateSMSApplication.getContext();
        ContentResolver contentResolver = context.getContentResolver();


        Conversation conversation = new Conversation();
        conversation.set_id(cursor.getString(cursor.getColumnIndex("_id")));
        conversation.setThread_id(cursor.getString(cursor.getColumnIndex("thread_id")));
        conversation.setSnippet(cursor.getString(cursor.getColumnIndex("snippet")));
        long date = Long.valueOf(cursor.getString(cursor.getColumnIndex("date")));
        String strDate = null;
        if (DateUtils.isToday(date)) {
            //是今天显示时分秒
            strDate = DateFormat.getTimeFormat(context).format(date);
        } else {
            //显示年月日
            strDate = DateFormat.getDateFormat(context).format(date);
        }
        conversation.setDate(strDate);
        conversation.setMsg_count(cursor.getString(cursor.getColumnIndex("msg_count")));


        //显示地址
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String name = ContactDao.getDisplayNameByAddress(contentResolver, address);
        conversation.setAddress(address);
        if (name != null) {
            conversation.setName(name);
        }


        //显示头像
        Bitmap avatar = ContactDao.getAvatarByAddress(contentResolver, address);
        conversation.setAvatar(avatar != null ? avatar : BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_person_yellow_a100_24dp));

        return conversation;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(String msg_count) {
        this.msg_count = msg_count;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
