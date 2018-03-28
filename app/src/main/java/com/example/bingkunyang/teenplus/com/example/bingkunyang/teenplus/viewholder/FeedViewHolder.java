package com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bingkunyang.teenplus.R;
import com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.model.Feed;

/**
 * Created by bingkunyang on 3/26/18.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder{


    public TextView emailView;
    public TextView contentView;

    public FeedViewHolder(View itemView) {
        super(itemView);

        emailView = itemView.findViewById(R.id.feed_email);
        contentView = itemView.findViewById(R.id.feed_content);
    }

    public void bindToFeed(Feed feed) {
        emailView.setText(feed.email);
        contentView.setText(feed.content);
    }

}


