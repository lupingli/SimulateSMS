package com.example.john.simulatesms.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.example.john.simulatesms.activity.ConversationDetailActivity;
import com.example.john.simulatesms.activity.SMSActivity;
import com.example.john.simulatesms.activity.SendNewSmsActivity;
import com.example.john.simulatesms.adapter.AutoSearchAdapter;
import com.example.john.simulatesms.adapter.ConversationCursorAdapter;
import com.example.john.simulatesms.adapter.ConversationDetailCursorAdapter;
import com.example.john.simulatesms.fragment.ConversationFragment;
import com.example.john.simulatesms.util.LogUtil;

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
                if (cookie != null && cookie instanceof ConversationCursorAdapter) {
                    ConversationCursorAdapter adapter = (ConversationCursorAdapter) cookie;
                    adapter.changeCursor(cursor);
                }
                break;
            case ConversationDetailActivity.QUERY_CONVERSATION_DETAIL_TOKEN:
                if (cookie != null && cookie instanceof ConversationDetailCursorAdapter) {
                    ConversationDetailCursorAdapter adapter = (ConversationDetailCursorAdapter) cookie;
                    adapter.changeCursor(cursor);
                }
                break;
//            case SendNewSmsActivity.QUERY_CONTACTS_TOKEN:
//                if (cookie != null && cookie instanceof AutoSearchAdapter) {
//                    AutoSearchAdapter adapter = (AutoSearchAdapter) cookie;
//                    adapter.changeCursor(cursor);
//                }
//                break;
        }
    }
}
