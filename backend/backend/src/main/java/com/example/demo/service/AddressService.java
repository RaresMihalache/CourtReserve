package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAllAddresses(){
        List<Address> addressList = new ArrayList<>();

        try{
            addressList = addressRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return addressList;
    }

}
