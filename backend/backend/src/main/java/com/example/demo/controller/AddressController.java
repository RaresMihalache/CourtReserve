package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> findAllAddresses(){
        List<Address> addressList = new ArrayList<>();
        try{
            addressList = addressService.findAllAddresses();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return addressList;
    }
}
