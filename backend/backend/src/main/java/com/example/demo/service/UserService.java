package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Encrypting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(User user) {
        try {
            user.setPassword(Encrypting.encrypt(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("register - Service");
            return false;
        }

        return true;
    }

    public boolean findUserByEmail(String email){
        try{
            User user = userRepository.findUserByEmail(email);
            if(user != null){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("find user - Service");
        }
        return false;
    }

    public List<User> findAllUsers(){
        List<User> userList;
        try{
            userList = userRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Find all users - Service");
            return null;
        }

        return userList;
    }

    public User findUserById(String id){
        User user;
        try{
            user = userRepository.findUserById(Long.parseLong(id));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Find user by id - Service");
            return null;
        }

        return user;
    }

    public boolean deleteUserById(String id){
        try{
            userRepository.deleteById(Long.parseLong(id));

            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Delete user - Service");
        }

        return false;
    }

    public User updateUserDetails(User user, String userId){
        User userFound = null;
        try{
            userFound = userRepository.findUserById(Long.parseLong(userId));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("find user to update - Service");
        }
        try{
            if(userFound != null){
                if(user.getName()!=null){
                    userFound.setName(user.getName());
                }
                if(user.getEmail() !=null){
                    userFound.setEmail(user.getEmail());
                }
                if(user.getPhoneNumber()!=null){
                    userFound.setPhoneNumber(user.getPhoneNumber());
                }
                userRepository.save(userFound);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to update user's details");
        }

        return userFound;
    }
}
