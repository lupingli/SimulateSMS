package com.example.john.simulatesms.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.ConversationAdapter;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.entity.Conversation;
import com.example.john.simulatesms.ui.activity.ConversationDetailActivity;
import com.example.john.simulatesms.ui.activity.SMSActivity;
import com.example.john.simulatesms.util.ConstantUtil;
import com.example.john.simulatesms.util.LogUtil;

/**
 * Created by John on 2016/11/20.
 */

public class SearchFragment extends BaseFragment {

    private EditText etContent;
    private ListView searchLv;
    private SimpleQueryHandler simpleQueryHandler;
    private String[] projection;
    private ConversationAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        etContent = (EditText) view.findViewById(R.id.et_search_content);
        searchLv = (ListView) view.findViewById(R.id.search_list_view);
        return view;
    }

    @Override
    public void initData() {
        simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        projection = new String[]{
                "sms._id as _id",
                "sms.thread_id as thread_id",
                "sms.body as snippet",
                "groups.msg_count as msg_count",
                "sms.address as address",
                "sms.date as date"
        };
        adapter = new ConversationAdapter(getContext(), null, CursorAdapter.FLAG_AUTO_REQUERY);
        searchLv.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        etContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String selection = "body like '%" + charSequence + "%'";
                simpleQueryHandler.startQuery(ConversationFragment.QUERY_TOKEN, adapter, ConstantUtil.URI.CONVERSATION_URL, projection, selection, null, "date desc");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //隐藏输入法
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = adapter.getConversation(i);
                ConversationDetailActivity.actionStart(getActivity(), conversation.getAddress(), conversation.getName(), conversation.getThread_id());
            }
        });

    }

    @Override
    public void handleClickEvent(View view) {

    }
}
