package com.example.john.simulatesms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.ui.activity.SendNewSmsActivity;
import com.example.john.simulatesms.util.StringUtil;

/**
 * Created by John on 2016/12/1.
 */

public class AutoSearchAdapter extends CursorAdapter {
    public AutoSearchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.layout_item_auto_search, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyViewHolder myViewHolder = getViewHolder(view);
        myViewHolder.tvContactName.setText(cursor.getString(cursor.getColumnIndex("display_name")));
        String phoneNumber = cursor.getString(cursor.getColumnIndex("data1"));
        myViewHolder.tvContactPhone.setText(StringUtil.getNewStyleText(phoneNumber, SendNewSmsActivity.getConstraint(), Color.RED));
    }

    public MyViewHolder getViewHolder(View view) {
        MyViewHolder myViewHolder = (MyViewHolder) view.getTag();
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        }
        return myViewHolder;
    }

    class MyViewHolder {
        private TextView tvContactName;
        private TextView tvContactPhone;

        public MyViewHolder(View view) {
            tvContactName = (TextView) view.findViewById(R.id.tv_contact_name);
            tvContactPhone = (TextView) view.findViewById(R.id.tv_contack_phone);
        }

    }


    /**
     * 将选中的条目转换为字符串
     *
     * @param cursor
     * @return
     */
    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("data1"));
    }
}
