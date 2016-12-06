package com.example.john.simulatesms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.entity.Group;

/**
 * Created by John on 2016/12/6.
 */

public class GroupAdapter extends CursorAdapter {
    public GroupAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.layout_item_group_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyViewHolder viewHolder = getViewHolder(view);
        Group group = Group.createGroupByCursor(cursor);
        viewHolder.groupName.setText(group.getGroup_name() + "(" + group.getThread_count() + ")");
        viewHolder.crateDate.setText(DateFormat.getDateFormat(context).format(group.getCreate_date()));
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
        private TextView groupName;
        private TextView crateDate;

        public MyViewHolder(View view) {
            groupName = (TextView) view.findViewById(R.id.tv_grouplist_name);
            crateDate = (TextView) view.findViewById(R.id.tv_grouplist_date);
        }
    }

    public Group getSingleGroup(int pos) {
        Cursor cursor = (Cursor) getItem(pos);
        return Group.createGroupByCursor(cursor);
    }
}
