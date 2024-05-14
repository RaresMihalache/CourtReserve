package com.example.demo.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long id;
    private Long roleId;
    private String status;

    public LoginResponse(Long id, Long roleId, String status) {
        this.id = id;
        this.roleId = roleId;
        this.status = status;
    }
}
