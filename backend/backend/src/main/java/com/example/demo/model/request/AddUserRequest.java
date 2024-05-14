package com.example.demo.model.request;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddUserRequest {
    private Long id;
    private User user;
}