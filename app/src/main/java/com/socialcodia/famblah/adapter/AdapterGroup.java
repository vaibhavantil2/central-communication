package com.socialcodia.famblah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.famblah.R;
import com.socialcodia.famblah.model.ModelGroup;

import java.util.List;

public class AdapterGroup extends RecyclerView.Adapter<AdapterGroup.ViewHolder> {

    List<ModelGroup> modelGroupList;
    Context context;

    public AdapterGroup(List<ModelGroup> modelGroupList, Context context) {
        this.modelGroupList = modelGroupList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelGroupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvGroupName,tvLastMessageSenderName,tvLastMessage;
        private ImageView groupImageIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvLastMessageSenderName = itemView.findViewById(R.id.tvLastMessageSenderName);
            groupImageIcon = itemView.findViewById(R.id.groupImageIcon);
        }
    }
}
