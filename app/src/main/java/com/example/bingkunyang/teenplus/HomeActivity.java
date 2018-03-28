package com.example.bingkunyang.teenplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.fragment.FeedFragment;
import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.fragment.SearchFragment;
import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.model.Feed;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by bingkunyang on 3/14/18.
 */


public class HomeActivity extends AppCompatActivity {

    private ActionBar toolbar;
    final FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the store fragment by default
        toolbar.setTitle("Feed");

        loadFragment(FeedFragment.newInstance());

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    toolbar.setTitle("Feed");
                    fragment = FeedFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    toolbar.setTitle("Search");
                    fragment = SearchFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_messaging:
                    toolbar.setTitle("Messaging");
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Notifications");
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new_feed:
                return true;
            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                return true;
        }

        return false;
    }

    public void createFeed(MenuItem item) {
        final EditText input = new EditText(HomeActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final String email = auth.getCurrentUser().getEmail();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create your feed")
               .setView(input)
               .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        String text = input.getText().toString();
                        Feed newFeed = new Feed(email, text);
                        mDatabase.child("feed").push().setValue(newFeed);
//                        mDatabase.child("users").child(auth.getUid()).setValue(newFeed)
                    }
               });
        AlertDialog alert = builder.create();
        alert.show();

    }

}