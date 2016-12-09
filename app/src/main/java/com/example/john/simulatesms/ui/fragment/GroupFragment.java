package com.example.john.simulatesms.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.GroupAdapter;
import com.example.john.simulatesms.dao.GroupDao;
import com.example.john.simulatesms.dao.GroupMappingThreadDao;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.dialog.InputDialog;
import com.example.john.simulatesms.dialog.ListDialog;
import com.example.john.simulatesms.entity.Group;
import com.example.john.simulatesms.entity.GroupMappingThread;
import com.example.john.simulatesms.ui.activity.GroupThreadActivity;
import com.example.john.simulatesms.ui.activity.SMSActivity;
import com.example.john.simulatesms.util.ConstantUtil;

/**
 * Created by John on 2016/11/20.
 */

public class GroupFragment extends BaseFragment {
    public static final int GROUP_TOKEN = 2;

    private ListView lvGroupContent;
    private Button btnCreateGroup;
    private GroupAdapter adapter;
    private SimpleQueryHandler simpleQueryHandler;


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        lvGroupContent = (ListView) view.findViewById(R.id.lv_group_content);
        btnCreateGroup = (Button) view.findViewById(R.id.btn_create_group);
        return view;
    }

    @Override
    public void initData() {
        adapter = new GroupAdapter(getActivity(), null);
        lvGroupContent.setAdapter(adapter);
        simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        simpleQueryHandler.startQuery(GROUP_TOKEN, adapter, ConstantUtil.URI.GROUP_URI, null, null, null, "create_date desc");

        lvGroupContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                ListDialog.showDialog(getActivity(), "操作", new String[]{"修改", "删除"}, new ListDialog.OnListDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                updateGroupName(position);
                                break;
                            case 1:
                                //删除
                                deleteGroup(position);
                                break;
                        }
                    }
                });
                return true;
            }
        });

        lvGroupContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Group group = adapter.getSingleGroup(i);
                if (group.getThread_count() > 0) {
                    Log.d(SMSActivity.TAG, group.getGroup_name() + "-----" + group.get_id());
                    GroupThreadActivity.actionStart(getActivity(), group.getGroup_name(), group.get_id());
                } else {
                    Toast.makeText(getActivity(), "该分组没有添加会话", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 删除分组
     *
     * @param position
     */
    private void deleteGroup(int position) {
        Group group = adapter.getSingleGroup(position);
        GroupMappingThreadDao.deleteByGroupId(getActivity().getContentResolver(), group.get_id());
        GroupDao.delete(getActivity().getContentResolver(), group);
    }

    /**
     * 修改名字
     */
    private void updateGroupName(final int position) {
        //修改
        InputDialog.showDialog(getActivity(), "修改", new InputDialog.OnInputDialogListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm(String text) {
                if (!TextUtils.isEmpty(text)) {
                    Group group = adapter.getSingleGroup(position);
                    group.setGroup_name(text);
                    GroupDao.update(getActivity().getContentResolver(), group);
                }
            }
        });
    }

    @Override
    public void initListener() {
        btnCreateGroup.setOnClickListener(this);

    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_create_group:
                InputDialog.showDialog(getActivity(), "创建分组", new InputDialog.OnInputDialogListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onConfirm(String text) {
                        if (!TextUtils.isEmpty(text)) {
                            if (!GroupDao.hasGroup(getActivity().getContentResolver(), text)) {
                                Group group = new Group();
                                group.setGroup_name(text);
                                group.setCreate_date(System.currentTimeMillis());
                                group.setThread_count(0);
                                GroupDao.insert(getActivity().getContentResolver(), group);
                            } else {
                                Toast.makeText(getActivity(), "该分组已经存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
        }

    }
}
