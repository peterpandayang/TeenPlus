package com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bingkunyang.teenplus.R;

/**
 * Created by bingkunyang on 3/15/18.
 */

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
