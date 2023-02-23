package com.example.myteacherlocatoradmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptar_notificationRecyclerView extends RecyclerView.Adapter<Adaptar_notificationRecyclerView.ViewHolder>{

    List<String> usernameList,statusList,timeList;

    public Adaptar_notificationRecyclerView(List<String> usernameList, List<String> statusList, List<String> timeList) {
        this.usernameList = usernameList;
        this.statusList = statusList;
        this.timeList = timeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.status_textView.setText(statusList.get(position));
        holder.time_textView.setText(timeList.get(position));
        holder.userName_textView.setText(usernameList.get(position));

        if (statusList.get(position).equals("entered")){
            holder.relativeLayout.setBackgroundResource(R.drawable.cyan_gradient);
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView status_textView;
        TextView time_textView;
        TextView userName_textView;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName_textView = itemView.findViewById(R.id.userName_textView);
            status_textView = itemView.findViewById(R.id.status_textView);
            time_textView = itemView.findViewById(R.id.time_textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
