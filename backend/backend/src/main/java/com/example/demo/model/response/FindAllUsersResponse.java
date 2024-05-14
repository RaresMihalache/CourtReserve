package com.example.demo.model.response;

import com.example.demo.model.User;

import java.util.List;

public class FindAllUsersResponse {
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
