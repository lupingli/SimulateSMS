package com.example.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.demo.util.StringUtil;

public class MainActivity extends AppCompatActivity {
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView) findViewById(R.id.tv_text);
        tvText.setText(StringUtil.getNewStyleText(tvText.getText().toString(), "12"));

    }
}
