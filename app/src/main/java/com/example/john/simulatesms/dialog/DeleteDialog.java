package com.example.john.simulatesms.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.activity.SMSActivity;
import com.example.john.simulatesms.interfaces.OnDeleteListener;
import com.example.john.simulatesms.util.LogUtil;

/**
 * Created by John on 2016/11/27.
 */

public class DeleteDialog extends BaseDialog {
    private TextView tvMsg;
    private ProgressBar pb;
    private Button btnInterrupt;
    private OnDeleteListener onDeleteListener;
    private int max;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    protected DeleteDialog(Context context, int max) {
        super(context, R.style.ConfirmDialogStyle);
        this.max = max;
    }

    public static DeleteDialog showDeleteDialog(Context context, int max, OnDeleteListener onDeleteListener) {
        DeleteDialog dialog = new DeleteDialog(context, max);
        dialog.setOnDeleteListener(onDeleteListener);
        dialog.show();
        return dialog;
    }

    @Override
    public void initView() {
        setContentView(R.layout.layout_delete_dialog);
        tvMsg = (TextView) findViewById(R.id.delete_dialog_tv_msg);
        pb = (ProgressBar) findViewById(R.id.delete_dialog_pb);
        btnInterrupt = (Button) findViewById(R.id.delete_dialog_btn_interrupt);
    }

    @Override
    public void initData() {
        tvMsg.setText("正在删除0/" + max);
        pb.setProgress(0);
        pb.setMax(max);
    }

    @Override
    public void initListener() {
        btnInterrupt.setOnClickListener(this);

    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.delete_dialog_btn_interrupt:
                if (onDeleteListener != null) {
                    onDeleteListener.interruptOperation();
                }
                break;
        }
    }

    /**
     * 删除进度
     *
     * @param progress
     */
    public void updateDeleteProgress(int progress) {
        pb.setProgress(progress);
        pb.refreshDrawableState();
        LogUtil.d(SMSActivity.TAG, "progress=" + progress);
        tvMsg.setText("正在删除" + progress + "/" + max);
        LogUtil.d(SMSActivity.TAG, "正在删除" + progress + "/" + max);

    }
}
