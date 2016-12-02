package com.example.john.simulatesms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.interfaces.OnConfirmListener;

/**
 * Created by John on 2016/11/27.
 */

public class ConfirmDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvMsg;
    private Button btnOk;
    private Button btnCancel;
    private OnConfirmListener onConfirmListener;
    private String title;
    private String msg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void showConfirmDialog(Context context, String title, String msg, OnConfirmListener onConfirmListener) {
        ConfirmDialog confirmDialog = new ConfirmDialog(context);
        confirmDialog.setOnConfirmListener(onConfirmListener);
        confirmDialog.setTitle(title);
        confirmDialog.setMsg(msg);
        confirmDialog.show();
    }

    public ConfirmDialog(Context context) {
        super(context, R.style.ConfirmDialogStyle);
    }


    @Override
    public void initView() {
        setContentView(R.layout.layout_confirm_dialog);
        tvTitle = (TextView) findViewById(R.id.confirm_dialog_tv_title);
        tvMsg = (TextView) findViewById(R.id.confirm_dialog_tv_msg);
        btnOk = (Button) findViewById(R.id.confirm_dialog_btn_ok);
        btnCancel = (Button) findViewById(R.id.confirm_dialog_btn_cancel);
    }


    @Override
    public void initData() {
        tvTitle.setText(title);
        tvMsg.setText(msg);
    }

    @Override
    public void initListener() {
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.confirm_dialog_btn_ok:
                if (onConfirmListener != null) {
                    this.dismiss();
                    onConfirmListener.onOk();
                }
                break;

            case R.id.confirm_dialog_btn_cancel:
                if (onConfirmListener != null) {
                    onConfirmListener.onCancel();
                    this.dismiss();
                }
                break;
        }

    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }


}
