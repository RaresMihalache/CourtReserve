package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.response.FindAllUsersResponse;
import com.example.demo.model.response.RegisterResponse;
import com.example.demo.model.response.StatusResponse;
import com.example.demo.service.UserService;
import com.example.demo.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping()
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    private RegisterResponse register(@RequestBody User newUser) {
        try {
            if (EmailValidator.isEmail(newUser.getEmail())) {
                if (!userService.findUserByEmail(newUser.getEmail())) {
                    if (!userService.register(newUser)) {
                        return new RegisterResponse(-1, "Register failed");
                    }

                } else {
                    return new RegisterResponse(-1, "You are already registered with this email");
                }
            } else {
                return new RegisterResponse(-1, "Invalid email address");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Register - Controller");
            return new RegisterResponse(-1, "Register failed, complete all fields!");
        }
        return new RegisterResponse(newUser.getId(), "You registered successfully!");
    }

    @GetMapping("/users")
    public FindAllUsersResponse findAllUsers() {
        FindAllUsersResponse usersResponse = new FindAllUsersResponse();
        try {
            usersResponse.setUserList(userService.findAllUsers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Get users - Controller");
            return null;
        }

        return usersResponse;
    }

    @GetMapping("/user/{userId}")
    public User findUserById(@PathVariable String userId) {
        User user = null;
        try {
            user = userService.findUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Find user by id - Controller");
            return null;
        }

        return user;
    }

    @DeleteMapping("/delete/{userId}")
    public StatusResponse deleteUserById(@PathVariable String userId) {
        boolean deleteState = false;
        try {
            deleteState = userService.deleteUserById(userId);
            if (deleteState) {
                return new StatusResponse("User deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete user - Controller");
        }

        return new StatusResponse("Delete user operation failed!");
    }

    @PutMapping("/update/{userId}")
    public User updateUserDetails(@RequestBody User updatedUser, @PathVariable String userId) {

        try {
            return userService.updateUserDetails(updatedUser, userId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("update user - Controller");
        }

        return null;

    }

}
