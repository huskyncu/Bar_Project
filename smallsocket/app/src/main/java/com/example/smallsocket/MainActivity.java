package com.example.smallsocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button sendButton;
    private TextView responseTextView;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        responseTextView = findViewById(R.id.responseTextView);

        // 當用戶點擊發送按鈕時，將用戶輸入的消息通過 socket 傳遞到 Python 服務器
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                if (!message.isEmpty()) {
                    sendToServer(message);
                    editText.setText("");
                }
            }
        });

        // 建立 socket 連接，並在單獨的線程中監聽服務器的回應
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.8.199", 7100);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    while (true) {
                        try{
                            String response = in.readLine();
                            if (response != null) {
                                Log.d("Message", response);

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        responseTextView.setText(response);
                                    }
                                });
                            }
                            else{
                                Log.d("Message", "nothing");
                            }
                        }
                        catch (Exception e) {
                            Log.d("Message", "error");
                            e.printStackTrace();

                        }
                    }

                } catch (IOException e) {
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

