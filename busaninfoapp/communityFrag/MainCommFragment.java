package com.example.busaninfoapp.communityFrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busaninfoapp.CommWriteActivity;
import com.example.busaninfoapp.Community;
import com.example.busaninfoapp.CommunityActivity;
import com.example.busaninfoapp.R;
import com.example.busaninfoapp.ReviewWriteActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainCommFragment extends Fragment{

    CommunityActivity activity;
    Boolean trans = true;
    private CommunityAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ImageView imageview;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (CommunityActivity) getActivity();
    }

    public MainCommFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_comm, container, false);
        DatabaseReference ref = database.getReference("community");
        RecyclerView recyclerView = v.findViewById(R.id.commRectclerView);
        adapter = new CommunityAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ref.orderByChild("writeTime").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Community addCommunity = snapshot.getValue(Community.class);
                adapter.addData(addCommunity);
                adapter.notifyItemInserted(adapter.getItemCount()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

}