package com.example.barapp;

import androidx.annotation.Nullable;
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
import android.speech.RecognitionListener;
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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private Button music_btn, detail_btn,  pay_btn, order_btn,menu_btn, speech_btn;
    private TextView music_text,username;
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

        music_btn = findViewById(R.id.music_btn);
        detail_btn = findViewById(R.id.detail_btn);
        pay_btn = findViewById(R.id.pay_btn);
        order_btn = findViewById(R.id.order_btn);
        menu_btn = findViewById(R.id.menu_btn);
        speech_btn = findViewById(R.id.speech_btn);
        music_text = findViewById(R.id.music_text);
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
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                bundle.putString("key", message);
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String message = username.getText().toString();
                bundle.putString("key", message);
                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tts = new TextToSpeech(this, this);
        speech_btn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_text.setText("speech_btn  clicked");
                speakOut("你今天想幹嘛？");

                // 延遲開始語音識別，確保語音識別不會將 TTS 引擎的語音誤認為使用者的語音
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        promptSpeechInput();
                    }
                }, 1300); // 延遲 3 秒



            }
        });

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
                    writer.println("speak " + text);

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
                                @Override
                                public void run() {
                                    tvText2.setText(value);
                                    speakOut(value);
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
        tvText2.setText("1");
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