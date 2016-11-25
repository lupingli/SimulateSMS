package com.example.john.simulatesms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.ConversationCursorAdapter;
import com.example.john.simulatesms.app.SimulateSMSApplication;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.util.ConstantUtil;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by John on 2016/11/20.
 */

public class ConversationFragment extends BaseFragment {
    /**
     * 动画时长
     */
    private final int ANIMATIONN_TIME = 500;

    /**
     * 常量
     */
    public static final int QUERY_TOKEN = 0;
    /**
     * 选择菜单中按钮
     */
    private Button btnEdit;
    private Button btnNewSms;


    /**
     * 操作菜单中按钮
     */
    private Button btnSelectAll;
    private Button btnSelectCancel;
    private Button btnDelete;


    /**
     * 选择菜单和操作菜单
     */
    private LinearLayout selectionMenu;
    private LinearLayout operationMenu;


    private ListView listView;

    @Override

    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, null);
        btnEdit = (Button) view.findViewById(R.id.ll_conversation_btn_edit);
        btnNewSms = (Button) view.findViewById(R.id.ll_conversation_btn_new_sms);

        btnSelectAll = (Button) view.findViewById(R.id.ll_conversation_btn_select_all);
        btnSelectCancel = (Button) view.findViewById(R.id.ll_conversation_btn_select_cancel);
        btnDelete = (Button) view.findViewById(R.id.ll_conversation_btn_delete);

        selectionMenu = (LinearLayout) view.findViewById(R.id.ll_conversation_selection_menu);
        operationMenu = (LinearLayout) view.findViewById(R.id.ll_conversation_operation_menu);
        listView = (ListView) view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void initData() {
        //查询会话信息
        ConversationCursorAdapter adapter = new ConversationCursorAdapter(SimulateSMSApplication.getContext(), null, CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setAdapter(adapter);


        SimpleQueryHandler simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        simpleQueryHandler.startQuery(QUERY_TOKEN, adapter, ConstantUtil.URI.CONVERSATION_URL, null, null, null, null);
    }

    @Override
    public void initListener() {
        btnEdit.setOnClickListener(this);
        btnNewSms.setOnClickListener(this);

        btnSelectAll.setOnClickListener(this);
        btnSelectCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ll_conversation_btn_edit:
                ViewPropertyAnimator.animate(selectionMenu).translationY(150.0f).setDuration(ANIMATIONN_TIME).start();
                ViewPropertyAnimator.animate(operationMenu).translationY(-1.0f).setDuration(ANIMATIONN_TIME).start();

                break;
            case R.id.ll_conversation_btn_select_cancel:
                ViewPropertyAnimator.animate(selectionMenu).translationY(-1.0f).setDuration(ANIMATIONN_TIME).start();
                ViewPropertyAnimator.animate(operationMenu).translationY(150.0f).setDuration(ANIMATIONN_TIME).start();
                break;
        }
    }
}
