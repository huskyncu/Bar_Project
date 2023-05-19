package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity2 extends AppCompatActivity {
    private  Socket socket;
    private  BufferedWriter out;
    private BufferedReader in;
    private Button register,login;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "register "+name.getText();
                if (!message.isEmpty()) {
                    sendToServer(message);
                    //textView.setText("0.0");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "login 1";
                if (!message.isEmpty()) {
                    sendToServer(message);
                    //textView.setText("0.0");
                }
            }
        });

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
                                    if (key.equals("register") && !value.equals("error")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("Message", "register success");
                                                // 创建一个Bundle对象
                                                Bundle bundle = new Bundle();
                                                String message = value;
                                                bundle.putString("key", message);
                                                // 执行打开Activity2的代码
                                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                //closeSocketAndOpenActivity2();
                                                Toast.makeText(MainActivity2.this, "register as "+message, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else if (key.equals("login") && !value.equals("error")&& !value.equals("No Data")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("Message", value);
                                                // 创建一个Bundle对象
                                                Bundle bundle = new Bundle();
                                                String message = value;
                                                bundle.putString("key", message);
                                                Toast.makeText(MainActivity2.this, "login as "+message, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                //closeSocketAndOpenActivity2();
                                                //Toast.makeText(MainActivity2.this, value, Toast.LENGTH_LONG).show();
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
}