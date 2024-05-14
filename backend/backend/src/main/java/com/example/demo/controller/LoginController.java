package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.response.GetUserResponse;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.StatusResponse;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/login")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping()
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response;
        try {
            response = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (response.getId() == null && response.getRoleId() == null) {
                return new LoginResponse(null, null, "Email or password are incorrect!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Login - Controller");
            return new LoginResponse(null, null, "Email or password are incorrect!");
        }

        return new LoginResponse(response.getId(), response.getRoleId(), "Logged in successfully!");
    }

    @PutMapping("/resetPassword")
    public StatusResponse resetPassword(@RequestBody LoginRequest request){
        try {
            return loginService.resetPassword(request.getEmail(), request.getPassword());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Reset password - Controller");
        }

        return new StatusResponse("Failed to reset password");
    }

    @GetMapping("/getByEmail/{email}")
    public GetUserResponse findUserByEmail(@PathVariable String email){
        GetUserResponse response;
        try {
            response = loginService.findUserByEmail(email);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Find by email - Controller");
            return new GetUserResponse(null, "Invalid email");
        }

        return response;
    }
}
