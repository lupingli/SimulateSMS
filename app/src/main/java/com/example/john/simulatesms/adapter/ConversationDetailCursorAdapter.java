package com.example.john.simulatesms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.entity.ConversationDetail;
import com.example.john.simulatesms.util.ConstantUtil;

/**
 * Created by John on 2016/11/28.
 */

public class ConversationDetailCursorAdapter extends CursorAdapter {

    private final int SMS_DURATION = 3 * 60 * 1000;
    private ListView listView;


    public ConversationDetailCursorAdapter(Context context, Cursor c, int flags, ListView listView) {
        super(context, c, flags);
        this.listView = listView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.layout_item_conversation_detail, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyViewHolder myViewHolder = getViewHolder(view);
        ConversationDetail conversationDetail = ConversationDetail.createConversationDetailFromCursor(cursor);

        if (cursor.getPosition() == 0) {
            showSmsDate(context, conversationDetail, myViewHolder);
        } else {
            long currentDate = Long.valueOf(conversationDetail.getDate());
            long previousDate = Long.valueOf(getPreviousDate(cursor.getPosition()));
            long duration = currentDate - previousDate;
            if (duration > SMS_DURATION) {
                showSmsDate(context, conversationDetail, myViewHolder);
            } else {
                myViewHolder.tvDate.setVisibility(View.GONE);
            }
        }

        int type = Integer.valueOf(conversationDetail.getType());
        if (type == ConstantUtil.SmsType.SENDER) {
            myViewHolder.tvSend.setText(conversationDetail.getBody());
            myViewHolder.tvSend.setVisibility(View.VISIBLE);
            myViewHolder.tvReceiver.setVisibility(View.GONE);
        } else if (type == ConstantUtil.SmsType.RECEIVER) {
            myViewHolder.tvReceiver.setText(conversationDetail.getBody());
            myViewHolder.tvReceiver.setVisibility(View.VISIBLE);
            myViewHolder.tvSend.setVisibility(View.GONE);
        }

    }


    /**
     * 得到当前短信时间
     *
     * @param context
     * @param conversationDetail
     * @return
     */
    private void showSmsDate(Context context, ConversationDetail conversationDetail, MyViewHolder viewHolder) {
        long date = Long.valueOf(conversationDetail.getDate());
        String strDate = null;
        if (DateUtils.isToday(date)) {
            strDate = DateFormat.getTimeFormat(context).format(date);
        } else {
            strDate = DateFormat.getDateFormat(context).format(date);
        }
        viewHolder.tvDate.setVisibility(View.VISIBLE);
        viewHolder.tvDate.setText(strDate);
    }

    /**
     * 得到上一条短信时间
     * int position
     *
     * @return
     */
    private long getPreviousDate(int position) {
        Cursor cursor = (Cursor) getItem(position - 1);
        ConversationDetail conversationDetail = ConversationDetail.createConversationDetailFromCursor(cursor);
        return Long.valueOf(conversationDetail.getDate());
    }

    private MyViewHolder getViewHolder(View view) {
        MyViewHolder viewHolder = (MyViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    class MyViewHolder {
        private TextView tvSend;
        private TextView tvReceiver;
        private TextView tvDate;

        public MyViewHolder(View view) {
            tvSend = (TextView) view.findViewById(R.id.tv_send);
            tvReceiver = (TextView) view.findViewById(R.id.tv_receiver);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }


    /**
     * 为了将listview显示的条目下到最新条目
     *
     * @param cursor
     */
    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        listView.setSelection(getCount());
    }
}
