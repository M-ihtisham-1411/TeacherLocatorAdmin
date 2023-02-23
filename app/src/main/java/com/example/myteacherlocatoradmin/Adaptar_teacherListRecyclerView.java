package com.example.myteacherlocatoradmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adaptar_teacherListRecyclerView extends RecyclerView.Adapter<Adaptar_teacherListRecyclerView.ViewHolder>{

    DatabaseReference teacherRef;

    Context context;
    List<String> nameList, userNameList;

    public Adaptar_teacherListRecyclerView(Context context,List<String> nameList, List<String> userNameList) {
        this.context = context;
        this.nameList = nameList;
        this.userNameList = userNameList;

        teacherRef = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_textView.setText(nameList.get(position));
        holder.userName_textView.setText(userNameList.get(position));

        holder.body_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,History.class);
                intent.putExtra("NAME",nameList.get(position));
                intent.putExtra("USERNAME",userNameList.get(position));

                context.startActivity(intent);
            }
        });

        holder.remove_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacherRef.child("Teachers").child(userNameList.get(position)).removeValue();
                teacherRef.child("teacher_history").child(userNameList.get(position)).removeValue();

                nameList.remove(position);
                userNameList.remove(position);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_textView;
        TextView userName_textView;
        RelativeLayout body_relativeLayout;
        TextView remove_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textView = itemView.findViewById(R.id.name_textView);
            userName_textView = itemView.findViewById(R.id.userName_textView);
            body_relativeLayout = itemView.findViewById(R.id.body_relativeLayout);
            remove_textView = itemView.findViewById(R.id.remove_textView);
        }
    }
}
