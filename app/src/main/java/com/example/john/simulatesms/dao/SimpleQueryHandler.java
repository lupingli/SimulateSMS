package com.example.john.simulatesms.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.example.john.simulatesms.adapter.ConversationCursorAdapter;
import com.example.john.simulatesms.fragment.ConversationFragment;

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
                    //adapter.changeCursor(null);

                }
                break;
        }
    }
}
