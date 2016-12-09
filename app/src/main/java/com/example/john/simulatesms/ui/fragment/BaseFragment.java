package com.example.john.simulatesms.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by John on 2016/11/20.
 * 在ViewPager中显示的fragment的基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView(inflater, container, savedInstanceState);
    }

    /**
     * 当fragment已经被完全创建好的时候调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 初始化fragment中的视图界面
     */
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    /**
     * 初始化fragment中数据
     */
    public abstract void initData();


    /**
     * 初始化监听
     */
    public abstract void initListener();


    /**
     * 点击事件处理
     *
     * @param view
     */
    public abstract void handleClickEvent(View view);

    @Override
    public void onClick(View view) {
        handleClickEvent(view);
    }
}
