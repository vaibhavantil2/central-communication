package com.socialcodia.famblah.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.socialcodia.famblah.R;
import com.socialcodia.famblah.adapter.AdapterGroup;
import com.socialcodia.famblah.model.ModelGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    RecyclerView groupsRecyclerView;
    List<ModelGroup> modelGroupList;

    //Firebase

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View famblah = inflater.inflate(R.layout.fragment_groups, container, false);

        //Firebase init

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        //ui init

        groupsRecyclerView = famblah.findViewById(R.id.groupsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        groupsRecyclerView.setLayoutManager(layoutManager);

        modelGroupList = new ArrayList<>();


        getGroups();
        return  famblah;
    }

    private void getGroups()
    {
        mRef.child("Groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelGroupList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    ModelGroup modelGroup = ds.getValue(ModelGroup.class);
                    modelGroupList.add(modelGroup);
                }
                AdapterGroup adapterGroup = new AdapterGroup(modelGroupList,getContext());
                groupsRecyclerView.setAdapter(adapterGroup);
                adapterGroup.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
