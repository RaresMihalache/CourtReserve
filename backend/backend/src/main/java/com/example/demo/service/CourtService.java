package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.dtos.CourtDTO;
import com.example.demo.dtos.builders.CourtBuilder;
import com.example.demo.model.Address;
import com.example.demo.model.Court;
import com.example.demo.model.Reservation;
import com.example.demo.model.Tariff;
import com.example.demo.repository.CourtRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class CourtService {

    private final CourtRepository courtRepository;

    private final ReservationRepository reservationRepository;

    public CourtService(CourtRepository courtRepository, ReservationRepository reservationRepository) {
        this.courtRepository = courtRepository;
        this.reservationRepository = reservationRepository;
    }

    public CourtDTO saveCourt(Court court) {

        return CourtBuilder.toCourtDTO(courtRepository.save(court));
    }

    public CourtDTO getCourt(Long id) {
        Optional<Court> court = Optional.ofNullable(courtRepository.getCourtById(id));
        CourtDTO courtDTO = CourtBuilder.toCourtDTO(court.get());

        return courtDTO;
    }

    public List<CourtDTO> findAll() {
        List<Court> courts = courtRepository.findAll();
        return courts.stream().map(CourtBuilder::toCourtDTO)
                .collect(Collectors.toList());
    }

    public CourtDTO updateCourt(Court court) {

        Court courtDB = courtRepository.getById(court.getId());

        if (Objects.nonNull(court.getSurface())) {
            courtDB.setSurface(court.getSurface());
        }

        if (Objects.nonNull(court.getStatus()) && !"".equalsIgnoreCase(court.getStatus())) {
            courtDB.setStatus(court.getStatus());
        }

        if(Objects.nonNull(court.getNumber())){
            courtDB.setNumber(court.getNumber());
        }

        if(Objects.nonNull(court.getTariff())){
            courtDB.setTariff(court.getTariff());
        }

        return CourtBuilder.toCourtDTO(courtRepository.save(courtDB));
    }

    public void deleteCourt(Long id) {
        courtRepository.deleteCourtById(id);
    }

    public List<CourtDTO> getCourtsByAddress(Address address) {
        List<Court> courts = courtRepository.findAll();
        List<Court> courtsByAddress = new ArrayList<>();
        for (Court court : courts) {
            if (court.getAddress().getCity().equals(address.getCity()))
                if (court.getAddress().getStreet().equals(address.getStreet()))
                    if (court.getAddress().getNumber().equals(address.getNumber()))
                        courtsByAddress.add(court);
        }
        return courtsByAddress.stream().map(CourtBuilder::toCourtDTO)
                .collect(Collectors.toList());
    }

    public Tariff getCourtTariff(Long id){
        return courtRepository.getCourtById(id).getTariff();
    }

}
