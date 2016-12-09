package com.example.john.simulatesms.ui.activity;

import android.Manifest;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

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
