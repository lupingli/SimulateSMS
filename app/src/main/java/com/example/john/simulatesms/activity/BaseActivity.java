package com.example.john.simulatesms.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 所有活动基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    /**
     * 初始话视图
     */
    public abstract void initView();


    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 处理点击事件
     *
     * @param view 事件源（控件）
     */
    public abstract void handleClickEvent(View view);


    /**
     * 点击事件处理
     *
     * @param view 控件
     */
    @Override
    public void onClick(View view) {
        handleClickEvent(view);
    }
}
