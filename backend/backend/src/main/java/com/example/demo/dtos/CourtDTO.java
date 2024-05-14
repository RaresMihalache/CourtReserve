package com.example.demo.dtos;

import com.example.demo.model.Address;
import com.example.demo.model.Reservation;
import com.example.demo.model.Tariff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourtDTO {

    private Long id;
    private Integer number;
    private String surface;
    private String status;
    private Tariff tariff;
    private Address address;

    private List<Reservation> reservations = new ArrayList<>();
}
