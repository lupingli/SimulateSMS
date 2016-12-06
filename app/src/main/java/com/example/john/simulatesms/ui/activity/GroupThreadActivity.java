package com.example.john.simulatesms.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.simulatesms.R;

public class GroupThreadActivity extends BaseActivity {
    private ListView listView;
    private ImageView ivBack;
    private TextView tvTitle;
    private String title;

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
