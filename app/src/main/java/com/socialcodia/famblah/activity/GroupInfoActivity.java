package com.socialcodia.famblah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.socialcodia.famblah.storage.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupInfoActivity extends AppCompatActivity {

    String groupId, name;

    Intent intent;

    private TextView tvGroupName, tvGroupDescription, tvCreatedBy;
    private ImageView groupImageIcon;

    //Firebase

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        tvGroupName = findViewById(R.id.tvGroupName);
        tvGroupDescription = findViewById(R.id.tvGroupDescription);
        tvCreatedBy = findViewById(R.id.tvCreatedBy);
        groupImageIcon = findViewById(R.id.groupImageIcon);

        //Firebase Init

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference();

        intent = getIntent();
        groupId = intent.getStringExtra("groupId");

        getGroupDetails(groupId);
    }

    private void getGroupDetails(String groupId)
    {
        mRef.child(Constants.GROUPS).child(groupId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    //Get Data
                    String groupName = ds.child(Constants.GROUP_NAME).getValue(String.class);
                    String groupDesc = ds.child(Constants.GROUP_DESCRIPTION).getValue(String.class);
                    String createdBy = ds.child(Constants.GROUP_CREATOR).getValue(String.class);
                    String createdTime = ds.child(Constants.TIMESTAMP).getValue(String.class);
                    String groupImage = ds.child(Constants.GROUP_IMAGE).getValue(String.class);

                    mRef.child(Constants.USERS).child(createdBy).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name = dataSnapshot.child(Constants.USER_NAME).getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //Set Data

                    String createdByAndTime = "Created By " + name + " at " +getTime(createdTime);

                    tvGroupName.setText(groupName);
                    tvGroupDescription.setText(groupDesc);
                    tvCreatedBy.setText(createdByAndTime);

                    try {
                        Picasso.get().load(groupImage).into(groupImageIcon);
                    }
                    catch (Exception e )
                    {
                        Picasso.get().load(R.drawable.group_image).into(groupImageIcon);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getTime(String createdTime)
    {
        Long ts = Long.parseLong(createdTime);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:a");
        String time = sdf.format(new Date(ts));
        return time;
    }


}
