package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {
    private RadioGroup my_radio_group;
    private TextView question;
    private TextView username_o;
    private Button send_btn;
    private String choose = "";
    private String tmp = "1";
    private ArrayList<String> stringList = new ArrayList<>();  // 創建一個空的ArrayList
    private ArrayList<String> wineList = new ArrayList<>();
    private ArrayList<String> winetableList = new ArrayList<>();
    private int qindex=0;
    private int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        my_radio_group = findViewById(R.id.my_radio_group);
        question = findViewById(R.id.question);
        username_o = findViewById(R.id.username_o);
        send_btn = findViewById(R.id.send_btn);

        // 获取传递过来的Intent
        Intent intent = getIntent();
        // 检查Intent是否包含附带的Bundle
        if (intent != null && intent.getExtras() != null) {
            // 从Intent中获取Bundle对象
            Bundle bundle = intent.getExtras();

            // 从Bundle中获取字符串数据
            String message = bundle.getString("key");
            String message2 = bundle.getString("key2");
            // 使用获取到的字符串数据进行操作
            if (message != null) {
                // 执行操作
                username_o.setText(message);
            }
            if (message2 != null) {
                // 执行操作
                sum+=Integer.parseInt(message2);
            }
        }
        // 添加字符串到 ArrayList
        stringList.add("喜歡酸/甜(1/0)");
        stringList.add("想/不想喝醉(1/0)");
        stringList.add("喜歡/不喜歡氣泡(1/0)");
        stringList.add("貴/便宜(1/0)");
        stringList.add("喜歡/不喜歡水果味(1/0)");
        wineList.add("伏特加七喜");
        wineList.add("伏特加萊姆");
        wineList.add("威士忌可樂");
        wineList.add("琴通寧");
        wineList.add("長島冰茶");
        wineList.add("性慾海灘");
        wineList.add("柯夢波丹");
        wineList.add("海風");
        wineList.add("龍舌蘭日出");
        winetableList.add("1010");
        winetableList.add("1000");
        winetableList.add("1011");
        winetableList.add("00000");
        winetableList.add("1111");
        winetableList.add("0101");
        winetableList.add("1101");
        winetableList.add("00001");
        winetableList.add("0100");
        question.setText(stringList.get(0));

        my_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_option1:
                        // Handle option 1
                        tmp="1";
                        break;
                    case R.id.radio_option2:
                        // Handle option 2
                        tmp="0";
                        break;
                    // Add more cases as per your need
                }
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(send_btn.getText().toString()=="send wine"){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket = new Socket("192.168.162.70", 7100);
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.println("order "+username_o.getText().toString()+" "+question.getText().toString());
                                out.flush();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    if(question.getText().toString().equals("性慾海灘") || question.getText().toString().equals("柯夢波丹") || question.getText().toString().equals("長島冰茶") || question.getText().toString().equals("威士忌可樂")){
                        sum+=400;
                    }else{
                        sum+=200;
                    }

                    Bundle bundle = new Bundle();
                    String message = username_o.getText().toString();
                    bundle.putString("key", message);
                    bundle.putString("key2", Integer.toString(sum));
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(MainActivity6.this, "已送出訂單！", Toast.LENGTH_SHORT).show();
                }
                choose+=tmp;
                if(winetableList.indexOf(choose)== -1){
                    qindex+=1;
                    question.setText(stringList.get(qindex));
                }else{
                    question.setText(wineList.get(winetableList.indexOf(choose)));
                    my_radio_group.setVisibility(View.INVISIBLE);
                    send_btn.setText("send wine");
                }

            }
        });
    }
}