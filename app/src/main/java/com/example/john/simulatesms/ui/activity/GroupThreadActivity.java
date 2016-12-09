package com.example.john.simulatesms.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.ConversationAdapter;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.entity.Conversation;
import com.example.john.simulatesms.entity.GroupMappingThread;
import com.example.john.simulatesms.ui.fragment.ConversationFragment;
import com.example.john.simulatesms.util.ConstantUtil;

public class GroupThreadActivity extends BaseActivity {
    private ListView listView;
    private ImageView ivBack;
    private TextView tvTitle;
    private ConversationAdapter adapter;

    public static void actionStart(Context context, String title, int groupId) {
        Intent intent = new Intent(context, GroupThreadActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("groupId", groupId);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_group_thread);
        initToolBar();
        listView = (ListView) findViewById(R.id.list_view);

    }

    private void initToolBar() {
        ivBack = (ImageView) findViewById(R.id.iv_title_bar_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int groupId = intent.getIntExtra("groupId", -1);
        tvTitle.setText(title);
        adapter = new ConversationAdapter(this, null, CursorAdapter.FLAG_AUTO_REQUERY);

        listView.setAdapter(adapter);
        //重新构造查询的列
        String[] projection = new String[]{
                "sms._id as _id",
                "sms.thread_id as thread_id",
                "sms.body as snippet",
                "groups.msg_count as msg_count",
                "sms.address as address",
                "sms.date as date"
        };

        SimpleQueryHandler simpleQueryHandler = new SimpleQueryHandler(getContentResolver());
        simpleQueryHandler.startQuery(ConversationFragment.QUERY_TOKEN, adapter, ConstantUtil.URI.CONVERSATION_URL, projection, buildSelection(groupId), null, null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = adapter.getConversation(i);
                ConversationDetailActivity.actionStart(GroupThreadActivity.this, conversation.getAddress(), conversation.getName(), conversation.getThread_id());
            }
        });

    }

    private String buildSelection(int groupId) {
        String selection = "";
        selection += "thread_id in(";
        Cursor cursor = getContentResolver().query(ConstantUtil.URI.GROUP_MAPPING_THREAD_URI, null, "group_id=?", new String[]{groupId + ""}, null);
        while (cursor.moveToNext()) {
            GroupMappingThread groupMappingThread = GroupMappingThread.createByCurosr(cursor);
            if (cursor.isLast()) {
                selection += groupMappingThread.getThread_id() + ")";
            } else {
                selection += groupMappingThread.getThread_id() + ",";
            }
        }

        return selection;
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);


    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
        }

    }
}
