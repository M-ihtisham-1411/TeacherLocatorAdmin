package com.example.myteacherlocatoradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherList extends AppCompatActivity {

    Adaptar_teacherListRecyclerView adaptar_teacherListRecyclerView;
    RecyclerView list_recyclerView;
    List<String> nameList, userNameList;

    DatabaseReference historyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        nameList = new ArrayList<>();
        userNameList = new ArrayList<>();

        list_recyclerView = findViewById(R.id.list_recyclerView);
        list_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyRef = FirebaseDatabase.getInstance().getReference("Teachers");


        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        nameList.add(snapshot1.child("name").getValue().toString());
                        userNameList.add(snapshot1.child("username").getValue().toString());
                    }

                    adaptar_teacherListRecyclerView = new Adaptar_teacherListRecyclerView(TeacherList.this,nameList, userNameList);
                    list_recyclerView.setAdapter(adaptar_teacherListRecyclerView);
                }else {
                    Toast.makeText(TeacherList.this, "No data exists!", Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}