package com.example.john.simulatesms.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.ConversationDetailAdapter;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.dao.SmsDao;
import com.example.john.simulatesms.util.ConstantUtil;

public class ConversationDetailActivity extends BaseActivity {
    public final static int QUERY_CONVERSATION_DETAIL_TOKEN = 1;

    private ImageView ivTitleImg;
    private TextView tvTitleMsg;
    private EditText etMsg;
    private String address;
    private String threadId;
    private ListView conversationDetail;
    private Button btnSend;
    private ConversationDetailAdapter adapter;
    private SimpleQueryHandler simpleQueryHandler;
    private String name;


    /**
     * 活动跳转
     *
     * @param context
     * @param address
     * @param threadId
     */
    public static void actionStart(Context context, String address, String name, String threadId) {
        Intent intent = new Intent(context, ConversationDetailActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("threadId", threadId);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_conversation_detail);
        initTitleBar();
        conversationDetail = (ListView) findViewById(R.id.lv_detail);
        conversationDetail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        btnSend = (Button) findViewById(R.id.btn_send);
        etMsg = (EditText) findViewById(R.id.et_msg);
    }

    private void initTitleBar() {
        ivTitleImg = (ImageView) findViewById(R.id.iv_title_bar_back);
        tvTitleMsg = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    @Override
    public void initData() {
        etMsg.setText("");
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        threadId = intent.getStringExtra("threadId");
        name = intent.getStringExtra("name");
//        LogUtil.d(SMSActivity.TAG, address + "---" + threadId);

        tvTitleMsg.setText(name == null ? address : name);
        //查询短信
        adapter = new ConversationDetailAdapter(this, null, CursorAdapter.FLAG_AUTO_REQUERY, conversationDetail);
        conversationDetail.setAdapter(adapter);
        simpleQueryHandler = new SimpleQueryHandler(getContentResolver());
        String[] projection = new String[]{
                "_id",
                "body",
                "type",
                "date"
        };
        simpleQueryHandler.startQuery(QUERY_CONVERSATION_DETAIL_TOKEN, adapter, ConstantUtil.URI.CONVERSATION_DELETE_URL, projection, "thread_id=?", new String[]{threadId}, "date");
    }

    @Override
    public void initListener() {
        ivTitleImg.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_back:
                this.finish();
                break;

            case R.id.btn_send:
                String body = etMsg.getText().toString();
                if (!TextUtils.isEmpty(body)) {
                    //发送短信
                    SmsDao.sendSms(this, address, body);
                }
                break;
        }
    }
}
