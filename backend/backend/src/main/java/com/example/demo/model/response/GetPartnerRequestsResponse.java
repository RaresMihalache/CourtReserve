package com.example.demo.model.response;

import com.example.demo.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetPartnerRequestsResponse {
    private List<Reservation> reservations;
    private String status;

}
