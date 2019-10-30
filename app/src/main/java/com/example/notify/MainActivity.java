package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "mohan";
    private static final String CHANNEL_NAME = "mohan";
    private static final String CHANNEL_DESC = "mohan notification";
    private Button btn,btn2;

    private EditText email;
    private EditText password;
    private Button login;
    //    private Button register;
    private ProgressBar progressBar;
    private Switch mess,sport,gen;
//    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mess= findViewById(R.id.mess);
        sport = findViewById(R.id.sports);
        gen = findViewById(R.id.general);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NotificationLog.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mess.isChecked()){
                    FirebaseMessaging.getInstance().subscribeToTopic("mess");
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("mess");
                }
                if(sport.isChecked()){
                    FirebaseMessaging.getInstance().subscribeToTopic("sports");
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("sports");
                }
                if(gen.isChecked()){
                    FirebaseMessaging.getInstance().subscribeToTopic("general");
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("general");
                }

            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
