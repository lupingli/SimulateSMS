package com.example.john.simulatesms.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.io.InputStream;

/**
 * Created by John on 2016/11/25.
 */

public class ContactDao {
    /**
     * 根据号码来获取联系人姓名
     *
     * @param contentResolver
     * @param address
     * @return
     */
    public static String getDisplayNameByAddress(ContentResolver contentResolver, String address) {
        String name = null;
        if (!TextUtils.isEmpty(address)) {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    name = cursor.getString(0);
                    //  LogUtil.d(SMSActivity.TAG, "name--" + name);
                }
                cursor.close();
            }
        }
        return name;
    }


    /**
     * 根据号码获取联系人头像
     *
     * @param contentResolver
     * @param address
     * @return
     */
    public static Bitmap getAvatarByAddress(ContentResolver contentResolver, String address) {
        Bitmap avatar = null;
        String name = ContactDao.getDisplayNameByAddress(contentResolver, address);
       // LogUtil.d(SMSActivity.TAG, name != null ? name : "");
        if (!TextUtils.isEmpty(address)) {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup._ID}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String _id = cursor.getString(0);
                    //拿到图片流
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, _id));
                    avatar = BitmapFactory.decodeStream(inputStream);
                }
            }
        }
        return avatar;
    }
}
