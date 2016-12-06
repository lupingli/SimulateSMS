package com.example.john.simulatesms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.simulatesms.R;

/**
 * Created by John on 2016/12/6.
 */

public class ListDialog extends BaseDialog {

    private TextView tvTitle;
    private ListView lvListDialog;
    private OnListDialogListener onListDialogListener;
    private String title;
    private String[] datas;

    public static void showDialog(Context context, String title, String[] datas, OnListDialogListener onListDialogListener) {
        ListDialog dialog = new ListDialog(context, title, datas, onListDialogListener);
        dialog.show();
    }

    public OnListDialogListener getOnListDialogListener() {
        return onListDialogListener;
    }

    public void setOnListDialogListener(OnListDialogListener onListDialogListener) {
        this.onListDialogListener = onListDialogListener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected ListDialog(Context context, String title, String[] datas, OnListDialogListener onListDialogListener) {
        super(context, R.style.ConfirmDialogStyle);
        this.title = title;
        this.datas = datas;
        this.onListDialogListener = onListDialogListener;
    }

    @Override
    public void initView() {
        setContentView(R.layout.layout_list_dialog);
        tvTitle = (TextView) findViewById(R.id.list_dialog_tv_title);
        lvListDialog = (ListView) findViewById(R.id.lv_list_dialog);
    }

    @Override
    public void initData() {
        tvTitle.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.layout_list_item_dialog, datas);
        lvListDialog.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        lvListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onListDialogListener != null) {
                    onListDialogListener.onItemClick(adapterView, view, i, l);
                }
                dismiss();
            }
        });

    }

    @Override
    public void handleClickEvent(View view) {

    }

    public interface OnListDialogListener {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }
}
