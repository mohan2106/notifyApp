package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class NotificationLog extends AppCompatActivity {
    private RecyclerView recyclerView;
    private notiAdapter adapter;
    private ArrayList<notiClass> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_log);

        recyclerView = findViewById(R.id.notiLogRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemList.add(new notiClass("kjhdfj","title","body of the notification"));
        itemList.add(new notiClass("kjhdfj","title","body of the notification"));
        itemList.add(new notiClass("kjhdfj","title","body of the notificaefbhtion"));
        itemList.add(new notiClass("kjhdfj","title","body of the notifjfhication"));
        itemList.add(new notiClass("kjhdfj","title","body of the notification"));
        itemList.add(new notiClass("kjhdfj","title","body of the notificationdkjsb"));
        itemList.add(new notiClass("kjhdfj","title","body of thdjhge notification"));
        itemList.add(new notiClass("kjhdfj","title","body of the djbjhdsnotification"));
        itemList.add(new notiClass("kjhdfj","title","body ofkjdfjk the notification"));
        itemList.add(new notiClass("kjhdfj","title","body of the dkffjhkjhnotification"));
        adapter = new notiAdapter(itemList,this);
        recyclerView.setAdapter(adapter);

    }
}
