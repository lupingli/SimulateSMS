package com.example.john.simulatesms.dao;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import com.example.john.simulatesms.activity.SMSActivity;
import com.example.john.simulatesms.app.SimulateSMSApplication;
import com.example.john.simulatesms.receiver.SmsReceiver;
import com.example.john.simulatesms.util.ConstantUtil;
import com.example.john.simulatesms.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by John on 2016/11/29.
 */

public class SmsDao {
    public static void sendSms(Context context, String address, String body) {
        LogUtil.d(SMSActivity.TAG, address);
        // sendSmsBySystemSms(context, address, body);
        sendSmsBySmsManager(context, address, body);
    }

    public static void insertSms(String body, String address) {
        ContentValues values = new ContentValues();
        values.put("body", body);
        values.put("address", address);
        values.put("type", ConstantUtil.SmsType.SENDER);
        SimulateSMSApplication.getContext().getContentResolver().insert(ConstantUtil.URI.CONVERSATION_DELETE_URL, values);
    }

    /**
     * 调用系统sms发送短信
     *
     * @param context
     * @param address
     * @param body
     */
    public static void sendSmsBySystemSms(Context context, String address, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("smsto:" + address);
        intent.setData(uri);
        intent.putExtra("sms_body", body);
        context.startActivity(intent);
    }

    /**
     * 自带api发送短信
     *
     * @param context
     * @param address
     * @param body
     */
    public static void sendSmsBySmsManager(Context context, final String address, final String body) {
        SmsManager manager = SmsManager.getDefault();
        Intent sentIntent = new Intent(context, SmsReceiver.class);
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, 0, sentIntent, PendingIntent.FLAG_ONE_SHOT);

        ArrayList<String> msgs = manager.divideMessage(body);
        for (String msg : msgs) {
            manager.sendTextMessage(address, null, msg, sentPendingIntent, null);
            //将短信插入到数据库
            insertSms(msg, address);
        }
    }
}
