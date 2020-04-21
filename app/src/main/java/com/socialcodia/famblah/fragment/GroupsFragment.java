package com.socialcodia.famblah.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.famblah.R;

public class GroupsFragment extends Fragment {

    RecyclerView groupsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View famblah = inflater.inflate(R.layout.fragment_groups, container, false);




        return  famblah;
    }
}
