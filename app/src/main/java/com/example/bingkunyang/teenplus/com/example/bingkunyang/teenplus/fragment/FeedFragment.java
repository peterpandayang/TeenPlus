package com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bingkunyang.teenplus.R;
import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.model.Feed;
import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.viewholder.FeedViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by bingkunyang on 3/15/18.
 */

public class FeedFragment extends Fragment{

    private FirebaseAuth auth;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("feed");
    private Query query;

    private FirebaseRecyclerAdapter<Feed, FeedViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private List<Feed> listFeed = new LinkedList<>();

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Map map = (Map) data.getValue();
                    Feed feed = new Feed((String)map.get("email"), (String)map.get("content"));
                    listFeed.add(feed);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        mRecycler = rootView.findViewById(R.id.recycler_view);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.feed_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        setUpFirebaseAdapter();
    }


    private void setUpFirebaseAdapter() {
        mAdapter = new FirebaseRecyclerAdapter<Feed, FeedViewHolder>
                (Feed.class, R.layout.feed_item, FeedViewHolder.class,
                        mDatabase) {

            @Override
            protected void populateViewHolder(FeedViewHolder viewHolder,
                                              Feed model, int position) {
                viewHolder.bindToFeed(model);
            }

        };

        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

}
