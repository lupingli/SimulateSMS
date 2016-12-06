package com.example.john.simulatesms.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.simulatesms.R;
import com.example.john.simulatesms.adapter.AutoSearchAdapter;
import com.example.john.simulatesms.dao.SimpleQueryHandler;
import com.example.john.simulatesms.dao.SmsDao;

public class SendNewSmsActivity extends BaseActivity {

    //    public static final int QUERY_CONTACTS_TOKEN = 2;
    private AutoCompleteTextView etSendPhone;
    private ImageView ivContacts;
    private EditText etSendContent;
    private Button btnSendNewSms;
    private ImageView ivTitleBack;
    private TextView tvTitlte;
//    private SimpleQueryHandler simpleQueryHandler;

    private static String constraint;

    public static String getConstraint() {
        return constraint;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SendNewSmsActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_send_new_sms);
        etSendPhone = (AutoCompleteTextView) findViewById(R.id.et_send_phone);
        ivContacts = (ImageView) findViewById(R.id.iv_contacts);
        etSendContent = (EditText) findViewById(R.id.et_send_content);
        btnSendNewSms = (Button) findViewById(R.id.btn_send_new_sms);
        ivTitleBack = (ImageView) findViewById(R.id.iv_title_bar_back);
        tvTitlte = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    @Override
    public void initData() {
        tvTitlte.setText("新建短信");

        final AutoSearchAdapter adapter = new AutoSearchAdapter(this, null, CursorAdapter.FLAG_AUTO_REQUERY);
        etSendPhone.setAdapter(adapter);
        final String[] projection = new String[]{
                "_id",
                "data1",
                "display_name"
        };
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                constraint = charSequence.toString();
                //异步处理查询效果
                //模糊查询
                String selection = "data1 like '%" + charSequence + "%'";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, null);
                return cursor;
            }
        });


    }

    @Override
    public void initListener() {
        ivTitleBack.setOnClickListener(this);
        btnSendNewSms.setOnClickListener(this);
        ivContacts.setOnClickListener(this);

    }

    @Override
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_title_bar_back:
                this.finish();
                break;
            case R.id.btn_send_new_sms:
                String address = etSendPhone.getText().toString();
                String content = etSendContent.getText().toString();
                if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(content)) {
                    SmsDao.sendSmsBySmsManager(this, address, content);
                } else {
                    Toast.makeText(this, "号码或短信内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iv_contacts:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("vnd.android.cursor.dir/contact");
                startActivityForResult(intent, 0);
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        if (data != null) {
            Uri uri = data.getData();
            String[] projectoin = new String[]{
                    "_id",
                    "has_phone_number"
            };
            Cursor cursor = getContentResolver().query(uri, projectoin, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex("has_phone_number"));
                if (hasPhoneNumber == 0) {
                    Toast.makeText(this, "没有号码", Toast.LENGTH_SHORT).show();
                } else {
                    String _id = cursor.getString(cursor.getColumnIndex("_id"));
                    Cursor contactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data1"}, "contact_id=" + _id, null, null);
                    contactCursor.moveToFirst();
                    String phoneNumber = contactCursor.getString(contactCursor.getColumnIndex("data1"));
                    etSendPhone.setText(phoneNumber);
                    etSendContent.requestFocus();
                }
            }
        }
    }
}
