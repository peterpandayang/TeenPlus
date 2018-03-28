package com.example.bingkunyang.teenplus.com.example.bingkunyang.teenplus.model;

/**
 * Created by bingkunyang on 3/18/18.
 */

public class Feed {

    public String email;
    public String content;

    public Feed(String email, String content){
        this.email = email;
        this.content = content;
    }

    public Feed(){}

    public String getEmail(){
        return email;
    }

    public String getContent(){
        return content;
    }

}
