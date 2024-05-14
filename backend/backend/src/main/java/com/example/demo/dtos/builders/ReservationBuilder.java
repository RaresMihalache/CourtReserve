package com.example.demo.dtos.builders;

import com.example.demo.dtos.ReservationDTO;
import com.example.demo.model.Reservation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationBuilder {

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getStartTime(), reservation.getEndTime(),
                reservation.getCourt(), reservation.getUsers());
    }
}
