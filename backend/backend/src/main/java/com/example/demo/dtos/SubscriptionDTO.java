package com.example.demo.dtos;

import com.example.demo.model.Reservation;
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
public class SubscriptionDTO {

    private Long id;
    private List<Reservation> reservations = new ArrayList<>();
}
