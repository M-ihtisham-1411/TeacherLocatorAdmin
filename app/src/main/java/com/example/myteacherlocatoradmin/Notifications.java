package com.example.myteacherlocatoradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    Adaptar_notificationRecyclerView adaptar_notificationRecyclerView;
    RecyclerView notification_recyclerView;

    DatabaseReference historyRef;
    List<String> usernameList,statusList,timeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);



        usernameList = new ArrayList<>();
        statusList = new ArrayList<>();
        timeList = new ArrayList<>();

        historyRef = FirebaseDatabase.getInstance().getReference("notification");

        notification_recyclerView = findViewById(R.id.notification_recyclerView);
        notification_recyclerView.setLayoutManager(new LinearLayoutManager(this));


        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("called");
                System.out.println(snapshot);
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    usernameList.add(snapshot1.child("user_name").getValue().toString());
                    statusList.add(snapshot1.child("action").getValue().toString());
                    timeList.add(snapshot1.child("time").getValue().toString());
                }

                adaptar_notificationRecyclerView = new Adaptar_notificationRecyclerView(usernameList,statusList,timeList);
                notification_recyclerView.setAdapter(adaptar_notificationRecyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}