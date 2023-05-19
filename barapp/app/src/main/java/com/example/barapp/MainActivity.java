package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Button start_btn;
    private TextView textView;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = findViewById(R.id.start_btn);
        textView = findViewById(R.id.textView);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "startprogram 1";
                if (!message.isEmpty()) {
                    sendToServer(message);
                    //textView.setText("0.0");
                }
            }
        });
        // 建立 socket 連接，並在單獨的線程中監聽服務器的回應
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.10.70", 7100);
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
                                    if (key.equals("startprogram") && value.equals("1")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("Message", "77777");
                                                // 执行打开Activity2的代码

                                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                                                startActivity(intent);
                                                Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                                                //closeSocketAndOpenActivity2();
                                            }
                                        });
                                    } else if(key.equals("startprogram")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("Message", "no sit");
                                                Toast.makeText(MainActivity.this, "no sit", Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                }
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
    private void closeSocketAndOpenActivity2() {
        /*try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
            Log.d("Message", "77777");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Log.d("Message", "77777");
        // 执行打开Activity2的代码
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        startActivity(intent);
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