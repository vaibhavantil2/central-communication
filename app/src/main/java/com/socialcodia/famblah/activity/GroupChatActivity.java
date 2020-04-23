package com.socialcodia.famblah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.socialcodia.famblah.R;
import com.socialcodia.famblah.model.ModelGroup;
import com.socialcodia.famblah.storage.Constants;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class GroupChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private TextView tvGroupName, tvGroupStatus;
    private EditText inputGroupMessage;
    private ImageView ivAttachGroupFile, ivSendGroupMessage, groupImageIcon;
    private Toolbar mToolbar;
    private ActionBar actionBar;
    Intent intent;
    String groupId,userId;

    //Firebase

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference mStorageRef;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        //Ui Init
        tvGroupName = findViewById(R.id.tvGroupName);
        tvGroupStatus = findViewById(R.id.tvGroupStatus);
        inputGroupMessage = findViewById(R.id.inputGroupMessage);
        ivAttachGroupFile = findViewById(R.id.ivAttachGroupFile);
        ivSendGroupMessage = findViewById(R.id.ivSendGroupMessage);
        groupImageIcon = findViewById(R.id.groupImageIcon);
        recyclerView = findViewById(R.id.groupChatRecyclerView);
        mToolbar = findViewById(R.id.toolbar);

        //Firebase init

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference();
        mUser = mAuth.getCurrentUser();
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();

        //Get Data from intent
        intent = getIntent();
        groupId = intent.getStringExtra("gid");

        if (mUser!=null)
        {
            userId = mUser.getUid();
        }

        //Get Group Details
        getGroupsDetails();

        ivSendGroupMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateMessage();
            }
        });
    }

    private void ValidateMessage()
    {
        String message = inputGroupMessage.getText().toString().trim();
        if (message.isEmpty())
        {
            Toast.makeText(this, "Can't sent empty message", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SendMessage(message);
        }
    }

    private void SendMessage(String message)
    {
        mRef.child(Constants.GROUPS).child(groupId).child(Constants.CHATS);

        DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference(Constants.GROUPS).child(groupId).child(Constants.CHATS);

        String messageId = mChatRef.push().getKey();
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constants.GROUP_PARTICIPANT_ID,userId);
        map.put(Constants.TIMESTAMP,String.valueOf(System.currentTimeMillis()));
        map.put(Constants.CHAT_TYPE,"text");
        map.put(Constants.CHAT_MESSAGE,message);
        map.put(Constants.CHAT_STATUS,1);
        map.put(Constants.CHAT_MESSAGE_ID,messageId);
        mChatRef.child(messageId).setValue(map);
    }

    private void getGroupsDetails()
    {
        mRef.child(Constants.GROUPS).child(groupId)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String groupName = dataSnapshot.child(Constants.GROUP_NAME).getValue(String.class);
                String groupImage = dataSnapshot.child(Constants.GROUP_IMAGE).getValue(String.class);

                //Set value
                tvGroupName.setText(groupName);
                try {
                    Picasso.get().load(groupImage).into(groupImageIcon);
                }
                catch (Exception e)
                {
                    Picasso.get().load(R.drawable.person_female).into(groupImageIcon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
