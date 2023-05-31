package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity5 extends AppCompatActivity {

    private TextView username_m;
    private CheckBox v7_box;
    private CheckBox vl_box;
    private CheckBox wc_box;
    private CheckBox sex_box;
    private CheckBox cur_box;
    private CheckBox wind_box;
    private CheckBox gin_box;
    private CheckBox long_box;
    private CheckBox sun_box;
    private Button submit_m;

    int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        username_m = findViewById(R.id.username_m);
        v7_box = findViewById(R.id.v7_box);
        vl_box = findViewById(R.id.vl_box);
        wc_box = findViewById(R.id.wc_box);
        sex_box = findViewById(R.id.sex_box);
        cur_box = findViewById(R.id.cur_box);
        wind_box = findViewById(R.id.wind_box);
        gin_box = findViewById(R.id.gin_box);
        long_box = findViewById(R.id.long_box);
        sun_box = findViewById(R.id.sun_box);
        submit_m = findViewById(R.id.submit_m);

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
                username_m.setText(message);
            }
            if (message2 != null) {
                // 执行操作
                sum+=Integer.parseInt(message2);
            }
        }

        v7_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "伏特加七喜 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "伏特加七喜 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        vl_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "伏特加萊姆 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "伏特加萊姆 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wc_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "威士忌可樂 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "威士忌可樂 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sex_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "性慾海灘 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "性慾海灘 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cur_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "柯夢波丹 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "柯夢波丹 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wind_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "海風 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "海風 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gin_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "琴通寧 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "琴通寧 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        long_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "長島冰茶 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "長島冰茶 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sun_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Checkbox 被選中時的行為
                    Toast.makeText(MainActivity5.this, "龍舌蘭日出 checked", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox 沒有被選中時的行為
                    Toast.makeText(MainActivity5.this, "龍舌蘭日出 unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCheckedWines();
                Bundle bundle = new Bundle();
                String message = username_m.getText().toString();
                bundle.putString("key", message);
                bundle.putString("key2", Integer.toString(sum));
                Intent intent = new Intent(MainActivity5.this, MainActivity3.class);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(MainActivity5.this, "已送出訂單！", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendCheckedWines() {
        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.cur_box));
        checkBoxes.add((CheckBox) findViewById(R.id.gin_box));
        checkBoxes.add((CheckBox) findViewById(R.id.sex_box));
        checkBoxes.add((CheckBox) findViewById(R.id.sun_box));
        checkBoxes.add((CheckBox) findViewById(R.id.v7_box));
        checkBoxes.add((CheckBox) findViewById(R.id.vl_box));
        checkBoxes.add((CheckBox) findViewById(R.id.wc_box));
        checkBoxes.add((CheckBox) findViewById(R.id.wind_box));
        checkBoxes.add((CheckBox) findViewById(R.id.long_box));
        // Add more CheckBoxes to your list
        StringBuilder checkedWines = new StringBuilder();
        for (CheckBox checkbox : checkBoxes) {
            if (checkbox.isChecked()) {
                checkedWines.append(checkbox.getText().toString()).append(";");
                if(checkbox.getText().toString().equals("性慾海灘") || checkbox.getText().toString().equals("柯夢波丹") || checkbox.getText().toString().equals("長島冰茶") || checkbox.getText().toString().equals("威士忌可樂")){
                    sum+=400;
                }else{
                    sum+=200;
                }
            }
        }

        if (checkedWines.length() > 0) {
            checkedWines.setLength(checkedWines.length() - 1); // to remove the last comma
        }

        final String checkedWinesStr = checkedWines.toString();

        // Run the socket connection in a new thread to avoid NetworkOnMainThreadException
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.10.70", 7100);
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("menu "+username_m.getText().toString()+" "+checkedWinesStr);
                    out.flush();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}