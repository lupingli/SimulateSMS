package com.example.john.simulatesms.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import com.example.john.simulatesms.ui.activity.ConversationDetailActivity;
import com.example.john.simulatesms.adapter.ConversationAdapter;
import com.example.john.simulatesms.adapter.ConversationDetailAdapter;
import com.example.john.simulatesms.adapter.GroupAdapter;
import com.example.john.simulatesms.ui.fragment.ConversationFragment;
import com.example.john.simulatesms.ui.fragment.GroupFragment;

/**
 * Created by John on 2016/11/23.
 * 异步查询
 */

public class SimpleQueryHandler extends AsyncQueryHandler {
    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {

        switch (token) {
            case ConversationFragment.QUERY_TOKEN:
                if (cookie != null && cookie instanceof ConversationAdapter) {
                    ConversationAdapter adapter = (ConversationAdapter) cookie;
                    adapter.changeCursor(cursor);
                }
                break;
            case ConversationDetailActivity.QUERY_CONVERSATION_DETAIL_TOKEN:
                if (cookie != null && cookie instanceof ConversationDetailAdapter) {
                    ConversationDetailAdapter adapter = (ConversationDetailAdapter) cookie;
                    adapter.changeCursor(cursor);
                }
                break;

            case GroupFragment.GROUP_TOKEN:
                if (cookie != null && cookie instanceof GroupAdapter) {
                    GroupAdapter adapter = (GroupAdapter) cookie;
                    adapter.changeCursor(cursor);
                }
                break;
        }
    }
}
