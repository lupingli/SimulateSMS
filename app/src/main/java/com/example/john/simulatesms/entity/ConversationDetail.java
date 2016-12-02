package com.example.john.simulatesms.entity;

import android.database.Cursor;

/**
 * Created by John on 2016/11/28.
 */

public class ConversationDetail {
    private String _id;
    private String body;
    private String date;
    private String type;


    /**
     * 由cursor创建ConversationDetail对象
     *
     * @param cursor
     * @return
     */
    public static ConversationDetail createConversationDetailFromCursor(Cursor cursor) {
        String _id = cursor.getString(cursor.getColumnIndex("_id"));
        String body = cursor.getString(cursor.getColumnIndex("body"));
        String date = cursor.getString(cursor.getColumnIndex("date"));
        String type = cursor.getString(cursor.getColumnIndex("type"));
        return new ConversationDetail(_id, body, date, type);
    }

    public ConversationDetail(String _id, String body, String date, String type) {
        this._id = _id;
        this.body = body;
        this.date = date;
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
