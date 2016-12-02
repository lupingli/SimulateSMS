package com.example.demo.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

public class StringUtil {
    /**
     * 将scString中的只要找到了reString字符就将reString改变颜色
     *
     * @param scString
     * @param reString
     * @return
     */
    public static SpannableStringBuilder getNewStyleText(String scString, String reString, int color) {
        int scStrLen = scString.length();
        int reStrLen = reString.length();
        int start = 0;
        int end = 0;
        SpannableStringBuilder style = new SpannableStringBuilder(scString);
        // SpannableString style = new SpannableString(scString);

        start = scString.indexOf(reString);
        if (start != -1) {
            //首次
            end = start + reStrLen;
            style.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//            style.setSpan(new BackgroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end;
            end = start + reStrLen;
            while (end <= scStrLen) {
                String tempStr = scString.substring(start, end);
                if (tempStr.equals(reString)) {
                    style.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                start = end;
                end = start + reStrLen;
            }
        }
        return style;
    }
}
