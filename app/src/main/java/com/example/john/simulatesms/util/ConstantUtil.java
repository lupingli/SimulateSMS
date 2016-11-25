package com.example.john.simulatesms.util;

import android.net.Uri;

/**
 * Created by John on 2016/11/21.
 */

public class ConstantUtil {
    public interface URI {
        Uri CONVERSATION_URL = Uri.parse("content://sms/conversations");
    }
}
