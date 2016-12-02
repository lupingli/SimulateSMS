package com.example.john.simulatesms.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by John on 2016/11/27.
 */

public abstract class BaseDialog extends AlertDialog implements OnClickListener {

    protected BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    public abstract void handleClickEvent(View view);

    @Override
    public void onClick(View view) {
        handleClickEvent(view);
    }
}
