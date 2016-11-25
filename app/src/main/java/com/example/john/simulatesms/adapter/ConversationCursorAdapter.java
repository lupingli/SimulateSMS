package com.example.john.simulatesms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by John on 2016/11/23.
 * 数据库发生变化，该adapter所绑定的视图也会立即发生变化
 */

public class ConversationCursorAdapter extends CursorAdapter {
    public ConversationCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    /**
     * listView的条目视图
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }


    /**
     * listView中条目所显示的类容
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
