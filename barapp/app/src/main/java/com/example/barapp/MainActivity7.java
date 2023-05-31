package com.example.barapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity7 extends AppCompatActivity {
    private TextView username;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView mes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        username = findViewById(R.id.username2);
        mes  = findViewById(R.id.mes);
        speak("客人您好請問您今天想幹嘛？");
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

        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {}
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    String userInput = matches.get(0);
                    // Send userInput to your server...
                    // Assume sendTextToServer is your function to send the text to your server
                    sendTextToServer(userInput);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}

            // Implement other methods as needed
        };

        speechRecognizer.setRecognitionListener(recognitionListener);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
                }
            }
        });



    }
    // Function to start listening
    public void startListening() {
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.startListening(intent2);
    }

    // Function to speak
    public void speak(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //        tts = new TextToSpeech(this, this);
//        speakOut("客人您好請問您今天想幹嘛？");
    public void sendTextToServer(String message) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("192.168.10.70", 7100);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send message to server
                out.println("speak "+message);

                // Receive response from server
                receiveResponseFromServer(in);

                // Speak the response

                // Close connections
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void receiveResponseFromServer(BufferedReader in) throws IOException {
        try{
            String response;
            while ((response = in.readLine()) != null) {
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
                            mes.setText(value);
                            speak(value);
                        }
                    });
                }
            }
        }
        catch (JSONException e) {
            Log.e("JSONException", "Invalid JSON data.", e);
        } catch (IOException e) {
            Log.e("IOException", "Error reading data.", e);
        }
    }

}