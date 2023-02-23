package com.example.myteacherlocatoradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    Adaptar_historyRecyclerView adaptar_historyRecyclerView;
    RecyclerView history_recyclerView;

    TextView name_textView;
    TextView userName_text;
    DatabaseReference historyRef;
    List<String> statusList,timeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        name_textView = findViewById(R.id.name_textView);
        userName_text = findViewById(R.id.userName_text);

        statusList = new ArrayList<>();
        timeList = new ArrayList<>();

        historyRef = FirebaseDatabase.getInstance().getReference("teacher_history/"+ getIntent().getExtras().getString("USERNAME"));

        history_recyclerView = findViewById(R.id.history_recyclerView);
        history_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        name_textView.setText(getIntent().getExtras().getString("NAME"));
        userName_text.setText(getIntent().getExtras().getString("USERNAME"));


        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot);
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    statusList.add(snapshot1.child("action").getValue().toString());
                    timeList.add(snapshot1.child("time").getValue().toString());
                    System.out.println(snapshot1.child("action").getValue().toString());
                }

                adaptar_historyRecyclerView = new Adaptar_historyRecyclerView(statusList,timeList);
                history_recyclerView.setAdapter(adaptar_historyRecyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}