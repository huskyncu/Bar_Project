package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private Button music_btn, detail_btn,  pay_btn, order_btn, speech_btn;
    private TextView music_text,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        music_btn = findViewById(R.id.music_btn);
        detail_btn = findViewById(R.id.detail_btn);
        pay_btn = findViewById(R.id.pay_btn);
        order_btn = findViewById(R.id.order_btn);
        speech_btn = findViewById(R.id.speech_btn);
        music_text = findViewById(R.id.music_text);
        username = findViewById(R.id.username);
        // 获取传递过来的Intent
        Intent intent = getIntent();
        // 检查Intent是否包含附带的Bundle
        if (intent != null && intent.getExtras() != null) {
            // 从Intent中获取Bundle对象
            Bundle bundle = intent.getExtras();

            // 从Bundle中获取字符串数据
            String message = bundle.getString("key");

            // 使用获取到的字符串数据进行操作
            if (message != null) {
                // 执行操作
                username.setText(message);
            }
        }
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("music_btn clicked");
            }
        });
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                bundle.putString("key", message);
                // 执行打开Activity2的代码
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("pay_btn clicked");
            }
        });
        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("order_btn clicked");
            }
        });
        speech_btn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("speech_btn  clicked");
            }
        });

    }
}