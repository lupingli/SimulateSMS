package com.example.john.simulatesms.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.dao.GroupDao;
import com.example.john.simulatesms.dao.GroupMappingThreadDao;
import com.example.john.simulatesms.dao.SmsDao;
import com.example.john.simulatesms.dialog.InputDialog;
import com.example.john.simulatesms.dialog.ListDialog;
import com.example.john.simulatesms.entity.Group;
import com.example.john.simulatesms.entity.GroupMappingThread;
import com.example.john.simulatesms.ui.activity.ConversationDetailActivity;
import com.example.john.simulatesms.ui.activity.SMSActivity;
import com.example.john.simulatesms.ui.activity.SendNewSmsActivity;
import com.example.john.simulatesms.adapter.ConversationAdapter;
import com.example.john.simulatesms.app.SimulateSMSApplication;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.dialog.ConfirmDialog;
import com.example.john.simulatesms.dialog.DeleteDialog;
import com.example.john.simulatesms.entity.Conversation;
import com.example.john.simulatesms.interfaces.OnConfirmListener;
import com.example.john.simulatesms.interfaces.OnDeleteListener;
import com.example.john.simulatesms.util.ConstantUtil;
import com.example.john.simulatesms.util.LogUtil;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

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

    private static final int DELETE_SUCCESS = 0;
    private static final int DELETE_PROGRESS = 1;

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

    private ConversationAdapter adapter;

    private SimpleQueryHandler simpleQueryHandler;

    private List<String> selectedConversations;

    private DeleteDialog dialog;

    private boolean isInterrupt;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELETE_SUCCESS:
                    adapter.setShowSelected(false);
                    adapter.notifyDataSetChanged();
                    showSelectionMenu();
                    dialog.dismiss();
                    break;

                case DELETE_PROGRESS:
                    dialog.updateDeleteProgress(msg.arg1 + 1);
                    break;
            }
        }
    };

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
        LogUtil.d(SMSActivity.TAG, "test");
        //查询会话信息
        adapter = new ConversationAdapter(SimulateSMSApplication.getContext(), null, CursorAdapter.FLAG_AUTO_REQUERY);

        selectedConversations = adapter.getSelectedConversation();
        //listView设置适配器（CursorAdapter）
        listView.setAdapter(adapter);
        //重新构造查询的列
        String[] projection = new String[]{
                "sms._id as _id",
                "sms.thread_id as thread_id",
                "sms.body as snippet",
                "groups.msg_count as msg_count",
                "sms.address as address",
                "sms.date as date"
        };
        simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        //
        simpleQueryHandler.startQuery(QUERY_TOKEN, adapter, ConstantUtil.URI.CONVERSATION_URL, projection, null, null, "date desc");
    }

    @Override
    public void initListener() {
        btnEdit.setOnClickListener(this);
        btnNewSms.setOnClickListener(this);

        btnSelectAll.setOnClickListener(this);
        btnSelectCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.isShowSelected()) {
                    adapter.getSingleConversation(i);
                } else {
                    //显示会话详细信息
                    Cursor cursor = (Cursor) adapter.getItem(i);
                    Conversation conversation = Conversation.createFromCursor(cursor);
                    ConversationDetailActivity.actionStart(getActivity(), conversation.getAddress(), conversation.getName(), conversation.getThread_id());
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapter.isShowSelected()) {
                    if (GroupDao.hasData(getActivity().getContentResolver())) {
                        final Conversation conversation = adapter.getConversation(i);
                        LogUtil.d(SMSActivity.TAG, "thread_id==" + conversation.getThread_id());
                        String name = GroupMappingThreadDao.getGroupNameByThreadId(getActivity().getContentResolver(), Integer.valueOf(conversation.getThread_id()));

                        LogUtil.d(SMSActivity.TAG, "name===" + name);
                        if (name == null) {
                            //没有找到
                            ListDialog.showDialog(getActivity(), "请选择分组", GroupDao.getAllGroup(getActivity().getContentResolver()), new ListDialog.OnListDialogListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Group group = GroupFragment.adapter.getSingleGroup(i);
                                    group.setThread_count(group.getThread_count() + 1);
                                    GroupDao.update(getActivity().getContentResolver(), group);
                                    //
                                    GroupMappingThread groupMappingThread = new GroupMappingThread();
                                    groupMappingThread.setGroup_id(group.get_id());
                                    groupMappingThread.setThread_id(Integer.valueOf(conversation.getThread_id()));
                                    GroupMappingThreadDao.insert(getActivity().getContentResolver(), groupMappingThread);
                                }
                            });
                        } else {
                            //找到了
                            String msg = "该会话属于【" + name + "】分组,是否要将其移除？";
                            ConfirmDialog.showConfirmDialog(getActivity(), "提示", msg, new OnConfirmListener() {
                                @Override
                                public void onOk() {
                                    int groupId = GroupMappingThreadDao.getGroupIdByThreadId(getActivity().getContentResolver(), Integer.valueOf(conversation.getThread_id()));
                                    Group group = GroupDao.getGroupById(getActivity().getContentResolver(), groupId);
                                    group.setThread_count(group.getThread_count() - 1);
                                    GroupDao.update(getActivity().getContentResolver(), group);
                                    GroupMappingThreadDao.deleteByThreadId(getActivity().getContentResolver(), Integer.valueOf(conversation.getThread_id()));
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    } else {
                        Toast.makeText(getContext(), "没有可选分组，请添加", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ll_conversation_btn_edit:
                showOperationMenu();
                adapter.setShowSelected(true);
                adapter.notifyDataSetChanged();
                break;

            case R.id.ll_conversation_btn_select_cancel:
                showSelectionMenu();
                adapter.setShowSelected(false);
                adapter.canceleddAllConversation();
                adapter.notifyDataSetChanged();
                break;

            case R.id.ll_conversation_btn_select_all:
                adapter.selectedAllConversation();
                break;

            //删除所选中的短信
            case R.id.ll_conversation_btn_delete:
                if (selectedConversations.size() == 0) {
                    return;
                } else {
                    ConfirmDialog.showConfirmDialog(getContext(), "提示", "确定要删除？", new OnConfirmListener() {
                        @Override
                        public void onOk() {
                            //删除选中的信息
                            deleteSms(selectedConversations);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    break;
                }
            case R.id.ll_conversation_btn_new_sms:
                SendNewSmsActivity.actionStart(getActivity());
                break;
        }
    }

    /**
     * 显示选择菜单
     */

    private void showSelectionMenu() {
        ViewPropertyAnimator.animate(selectionMenu).translationY(0).setDuration(ANIMATIONN_TIME);
        ViewPropertyAnimator.animate(operationMenu).translationY(operationMenu.getHeight()).setDuration(ANIMATIONN_TIME);
    }


    /**
     * 显示操作菜单
     */
    private void showOperationMenu() {
        ViewPropertyAnimator.animate(selectionMenu).translationY(selectionMenu.getHeight()).setDuration(ANIMATIONN_TIME);
        ViewPropertyAnimator.animate(operationMenu).translationY(0).setDuration(ANIMATIONN_TIME);
    }

    private void deleteSms(final List<String> threadIds) {

        //子线程创建对话框
        dialog = DeleteDialog.showDeleteDialog(getActivity(), selectedConversations.size(), new OnDeleteListener() {
            @Override
            public void interruptOperation() {
                //进行中断操作
                isInterrupt = true;

            }
        });


        LogUtil.d(SMSActivity.TAG, "hello");
        new Thread() {
            @Override
            public void run() {

                for (int i = 0; i < threadIds.size(); i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (isInterrupt) {
                        isInterrupt = false;
                        break;
                    }
                    getActivity().getContentResolver().delete(ConstantUtil.URI.CONVERSATION_DELETE_URL, "thread_id=?", new String[]{threadIds.get(i)});
                    LogUtil.d(SMSActivity.TAG, "delete");
                    Message msg = Message.obtain();
                    msg.what = DELETE_PROGRESS;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
                //必须要添加
                threadIds.clear();
                //删除完成发送消息
                Message msg = Message.obtain();
                msg.what = DELETE_SUCCESS;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
