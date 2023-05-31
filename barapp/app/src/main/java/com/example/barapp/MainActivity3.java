package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private Button detail_btn,  logout_btn , order_btn,menu_btn, speech_btn;
    private TextView username;
    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView tvText,tvText2;
    private TextToSpeech tts;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private  Socket socket;
    private  BufferedWriter out;
    private BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        detail_btn = findViewById(R.id.detail_btn);
        logout_btn = findViewById(R.id.logout_btn);
        order_btn = findViewById(R.id.order_btn);
        menu_btn = findViewById(R.id.menu_btn);
        speech_btn = findViewById(R.id.speech_btn);
        username = findViewById(R.id.username);
        tvText2 = findViewById(R.id.tvText);

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
                username.setText(message);
            }
            if (message2 != null) {
                // 执行操作
                int num1=Integer.parseInt(tvText2.getText().toString());
                int num2=Integer.parseInt(message2)+num1;
                tvText2.setText(Integer.toString(num2));
            }
        }
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                String message2 = tvText2.getText().toString();
                bundle.putString("key", message);
                bundle.putString("key2",message2 );
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        logout_btn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.10.70", 7100);
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.println("ppt 1");
                            out.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.10.70", 7100);
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.println("ppt 10");
                            out.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                bundle.putString("key", message);
                bundle.putString("key2", tvText2.getText().toString());
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.10.70", 7100);
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.println("ppt 11");
                            out.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                bundle.putString("key", message);
                bundle.putString("key2", tvText2.getText().toString());
                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tts = new TextToSpeech(this, this);
        speech_btn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //speakOut("你今天想幹嘛？");

                // 延遲開始語音識別，確保語音識別不會將 TTS 引擎的語音誤認為使用者的語音
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        promptSpeechInput();
                    }
                }, 1300); // 延遲 3 秒
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speakOut("可點語音服務並跟app說我要點酒。");
            }
        }, 1000); // 延遲 1 秒

    }
    private void sendAndReceiveFromServer(String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                OutputStream outputStream = null;
                PrintWriter writer = null;
                InputStream inputStream = null;
                BufferedReader reader = null;
                try {
                    // 建立一個 Socket 連接
                    socket = new Socket("192.168.10.70", 7100);

                    // 獲取輸出流，用於傳送資料
                    outputStream = socket.getOutputStream();
                    writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                    // 將文字資料傳送到伺服器
                    writer.println("speak " +username.getText().toString()+" "+ text);

                    // 獲取輸入流，用於接收資料
                    inputStream = socket.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String response;
                    while ((response = reader.readLine()) != null) {
                        Log.d("Message", response);
                        // Parse JSON and update list data
                        JSONObject jsonObject = new JSONObject(response);
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            String value = jsonObject.getString(key);
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    int num1,num2;
                                    if(value.equals("性慾海灘") || value.equals("柯夢波丹") || value.equals("長島冰茶") || value.equals("威士忌可樂")){
                                        num1=Integer.parseInt(tvText2.getText().toString());
                                        num2=num1+400;
                                        tvText2.setText(Integer.toString(num2));
                                        speakOut("已點"+value);
                                    }else if (value.equals("伏特加萊姆")||value.equals("海風")||value.equals("琴通寧")||value.equals("伏特加七喜")||value.equals(("龍舌蘭日出"))){
                                        num1=Integer.parseInt(tvText2.getText().toString());
                                        num2=num1+200;
                                        tvText2.setText(Integer.toString(num2));
                                        speakOut("已點"+value);
                                    }
                                    else{
                                        speakOut(value);
                                    }
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    Log.d("Message", "Error reading from server", e);
                } catch (JSONException e) {
                    Log.d("Message", "Error parsing JSON", e);
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (writer != null) {
                            writer.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        Log.e("Message", "Error closing resources", e);
                    }
                }
            }
        }).start();
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "說些什麼吧");

        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "您的裝置不支援語音識別！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    sendAndReceiveFromServer(result.get(0));
                    //speakOut(result.get(0));
                }
                break;
            }
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        //tvText2.setText("1");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.TRADITIONAL_CHINESE);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "此語言不支援");
            } else {
                speech_btn.setEnabled(true);
            }

        } else {
            Log.e("TTS", "初始化失敗");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


}