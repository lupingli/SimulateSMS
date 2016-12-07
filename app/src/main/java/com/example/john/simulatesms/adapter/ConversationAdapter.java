package com.example.john.simulatesms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.entity.Conversation;
import com.example.john.simulatesms.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2016/11/23.
 * 数据库发生变化，该adapter所绑定的视图也会立即发生变化
 * 控制数据显示
 */

public class ConversationAdapter extends CursorAdapter {

    /**
     * 是否显示选择菜单
     */
    private boolean isShowSelected;
    /**
     * 记录选中的会话
     */
    private List<String> selectedConversation = new ArrayList<>();

    public boolean isShowSelected() {
        return isShowSelected;
    }

    public void setShowSelected(boolean showSelected) {
        isShowSelected = showSelected;
    }


    public ConversationAdapter(Context context, Cursor c, int flags) {
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
        View view = View.inflate(context, R.layout.layout_item_conversation, null);
        return view;
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
        MyViewHolder myViewHolder = getViewHolder(view);
        final Conversation conversation = Conversation.createFromCursor(cursor);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String numberStr = conversation.getName() != null ? conversation.getName() : conversation.getAddress();
        String countStr = conversation.getMsg_count();
        SpannableString ss = new SpannableString(countStr);
        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 0, countStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(numberStr).append("(").append(ss).append(")");

        myViewHolder.tvPhoneNumber.setText(ssb);
        myViewHolder.tvNewMsg.setText(conversation.getSnippet());
        myViewHolder.tvDate.setText(conversation.getDate());
        myViewHolder.ivAvatar.setImageBitmap(conversation.getAvatar());

        if (isShowSelected) {
            myViewHolder.cb.setVisibility(View.VISIBLE);
            if (selectedConversation.contains(conversation.getThread_id())) {
                myViewHolder.cb.setChecked(true);
            } else {
                myViewHolder.cb.setChecked(false);
            }
        } else {
            myViewHolder.cb.setVisibility(View.GONE);
        }
    }


    /**
     * 得到viewHolder
     *
     * @param view
     * @return
     */
    private MyViewHolder getViewHolder(View view) {
        MyViewHolder myViewHolder = (MyViewHolder) view.getTag();
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        }
        return myViewHolder;
    }

    /**
     * 管理视图中控件
     */
    class MyViewHolder {
        private ImageView ivAvatar;
        private TextView tvDate;
        private TextView tvPhoneNumber;
        private TextView tvNewMsg;
        private CheckBox cb;

        public MyViewHolder(View view) {
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvNewMsg = (TextView) view.findViewById(R.id.tv_new_msg);
            tvPhoneNumber = (TextView) view.findViewById(R.id.tv_phone_number);
            cb = (CheckBox) view.findViewById(R.id.cb);
        }
    }

    /**
     * 用集合去记录选择的条目
     *
     * @param position
     */
    public void getSingleConversation(int position) {
        Cursor cursor = (Cursor) getItem(position);
        Conversation conversation = Conversation.createFromCursor(cursor);
        String thread_id = conversation.getThread_id();
        if (selectedConversation.contains(thread_id)) {
            selectedConversation.remove(thread_id);
        } else {
            selectedConversation.add(thread_id);
        }
        notifyDataSetChanged();
    }

    /**
     * 得到条目
     *
     * @param pos
     * @return
     */
    public Conversation getConversation(int pos) {
        return Conversation.createFromCursor((Cursor) getItem(pos));
    }

    /**
     * 选择所有的条目
     */
    public void selectedAllConversation() {

        Cursor cursor = getCursor();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            Conversation conversation = Conversation.createFromCursor(cursor);
            if (!selectedConversation.contains(conversation.getThread_id())) {
                selectedConversation.add(conversation.getThread_id());
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 取消所有选择的条目
     */
    public void canceleddAllConversation() {
        selectedConversation.clear();
    }


    public List<String> getSelectedConversation() {
        return selectedConversation;
    }

    public void setSelectedConversation(List<String> selectedConversation) {
        this.selectedConversation = selectedConversation;
    }
}
