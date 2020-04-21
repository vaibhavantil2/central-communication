package com.socialcodia.famblah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.famblah.R;
import com.socialcodia.famblah.activity.ChatActivity;
import com.socialcodia.famblah.model.ModelGroup;
import com.socialcodia.famblah.model.ModelUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;


public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.ViewHolder> {

    Context context;
    List<ModelUser> modelUserList;

    public AdapterChatList(Context context, List<ModelUser> modelUserList) {
        this.context = context;
        this.modelUserList = modelUserList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_chat_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelUserList.get(position).getName();
        String bio = modelUserList.get(position).getBio();
        String image = modelUserList.get(position).getImage();
        final String userId = modelUserList.get(position).getUid();


        holder.tvUserName.setText(name);
        holder.tvUserBio.setText(bio);
        try {
            Picasso.get().load(image).into(holder.userProfileImage);
        }
        catch (Exception e)
        {
            Picasso.get().load(R.drawable.person_female);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToChat(userId);
            }
        });

    }

    private void sendToChat(String userId)
    {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("hisUid",userId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return modelUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvUserName, tvUserBio;
        private ImageView userProfileImage;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserBio = itemView.findViewById(R.id.tvUserBio);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
