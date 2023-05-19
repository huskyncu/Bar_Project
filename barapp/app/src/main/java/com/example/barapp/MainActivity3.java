package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private Button music_btn, register_btn, login_btn, pay_btn, order_btn, speech_btn;
    private TextView music_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        music_btn = findViewById(R.id.music_btn);
        register_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.login_btn);
        pay_btn = findViewById(R.id.pay_btn);
        order_btn = findViewById(R.id.order_btn);
        speech_btn = findViewById(R.id.speech_btn);
        music_text = findViewById(R.id.music_text);

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("music_btn clicked");
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("register_btn clicked");
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("login_btn clicked");
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