package com.socialcodia.famblah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.socialcodia.famblah.R;
import com.socialcodia.famblah.model.ModelGroup;
import com.socialcodia.famblah.storage.Constants;
import com.squareup.picasso.Picasso;

public class GroupChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private TextView tvGroupName, tvGroupStatus;
    private EditText inputGroupMessage;
    private ImageView ivAttachGroupFile, ivSendGroupMessage, groupImageIcon;
    private Toolbar mToolbar;
    private ActionBar actionBar;
    Intent intent;
    String groupId;

    //Firebase

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference mStorageRef;

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

        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        //Get Data from intent
        intent = getIntent();
        groupId = intent.getStringExtra("gid");

        //Get Group Details
        getGroupsDetails(groupId);
    }

    private void getGroupsDetails(String groupId)
    {
        mRef.child(Constants.GROUPS).child(groupId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ModelGroup modelGroup = ds.getValue(ModelGroup.class);
                    String name = modelGroup.getName();
                    String image = modelGroup.getImage();
                    tvGroupName.setText(name);
                    try {
                        Picasso.get().load(image).into(groupImageIcon);
                    }
                    catch (Exception e)
                    {
                        Picasso.get().load(image).into(groupImageIcon);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
