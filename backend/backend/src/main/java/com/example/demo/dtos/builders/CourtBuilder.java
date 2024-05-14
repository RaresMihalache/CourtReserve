package com.example.demo.dtos.builders;

import com.example.demo.dtos.CourtDTO;
import com.example.demo.model.Court;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CourtBuilder {

    public static CourtDTO toCourtDTO(Court court) {
        return new CourtDTO(court.getId(), court.getNumber(), court.getSurface(), court.getStatus(),
                court.getTariff(), court.getAddress(), court.getReservations());
    }
}

