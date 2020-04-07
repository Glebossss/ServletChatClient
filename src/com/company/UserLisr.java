package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class UserLisr {

    private final List<User> list;

    public UserLisr(List<User> list) {
        Gson gson = new GsonBuilder().create();
        this.list = list;
    }

    public List<User> getList(){return list;}
}
