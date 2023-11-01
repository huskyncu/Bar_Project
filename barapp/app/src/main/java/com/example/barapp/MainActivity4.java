package com.example.barapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity4 extends AppCompatActivity {

    private Button sendButton;
    private Button identify_btn;
    private TextView responseTextView,money;
    private ListView listView;
    private ArrayList<String> listData = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String moneys;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        sendButton = findViewById(R.id.sendButton);
        identify_btn = findViewById(R.id.identify_btn);
        responseTextView = findViewById(R.id.responseTextView);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        // 获取传递过来的Intent
        Intent intent = getIntent();
        // 检查Intent是否包含附带的Bundle
        if (intent != null && intent.getExtras() != null) {
            // 从Intent中获取Bundle对象
            Bundle bundle = intent.getExtras();

            // 从Bundle中获取字符串数据
            String message = bundle.getString("key");
            moneys = bundle.getString("key2");
            // 使用获取到的字符串数据进行操作
            if (message != null) {
                // 执行操作
                responseTextView.setText(message);

            }

        }

        // 當用戶點擊發送按鈕時，將用戶輸入的消息通過 socket 傳遞到 Python 服務器
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String message = responseTextView.getText().toString();
                bundle.putString("key", message);
                bundle.putString("key2",moneys);
                // 执行打开Activity2的代码
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        identify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer("face_indentify "+responseTextView.getText().toString());
            }
        });

        // 建立 socket 連接，並在單獨的線程中監聽服務器的回應
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.162.70", 7100);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    while (true) {
                        try{
                            String response = in.readLine();
                            if (response != null) {
                                Log.d("Message", response);
                                // Parse JSON and update list data
                                JSONObject jsonObject = new JSONObject(response);
                                Iterator<String> keys = jsonObject.keys();
                                while(keys.hasNext()) {
                                    String key = keys.next();
                                    String value = jsonObject.getString(key);
                                    listData.add(key + ": " + value);
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("UI Thread", "Updating UI");
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                            else{
                                Log.d("Message", "nothing");
                            }
                        }
                        catch (JSONException e) {
                            Log.d("Message", "Error parsing JSON", e);
                        } catch (IOException e) {
                            Log.d("Message", "Error reading from socket", e);
                        }
                        catch (Exception e) {
                            Log.d("Message", "Unexpected error", e);
                        }
                    }

                } catch (IOException e) {
                    Log.d("Message", "here Unexpected error", e);
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("Message", "here 2 Unexpected error", e);
                    }
                }
            }
        }).start();
    }

    // 通過 socket 將消息傳遞到服務器
    private void sendToServer(String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (out != null) {
                        //out.write(message);
                        out.write(message);
                        out.flush();
                    }
                }
                catch (Exception e)
                {

                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

