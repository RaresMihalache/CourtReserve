package com.example.demo.controller;

import com.example.demo.dtos.CourtDTO;
import com.example.demo.dtos.ReservationDTO;
import com.example.demo.model.Reservation;
import com.example.demo.model.ReservationRequest;
import com.example.demo.model.request.AddUserRequest;
import com.example.demo.model.response.FindPlayersResponse;
import com.example.demo.model.response.GetPartnerRequestsResponse;
import com.example.demo.model.response.StatusResponse;
import com.example.demo.service.CourtService;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    private CourtService courtService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ReservationDTO> save(@RequestBody ReservationRequest reservationRequest) {
        ReservationDTO reservationDTO;
        try {
            reservationDTO = reservationService.saveReservation(reservationRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationService.getReservation(id);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        List<ReservationDTO> reservationDTO = reservationService.findAllReservations();
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateReservation(@RequestBody Reservation reservation) {
        reservationService.updateReservation(reservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllAvailableCourts/{data}/{startTime}/{endTime}", method = RequestMethod.GET)
    public ResponseEntity<List<CourtDTO>> findAllAvailableCourts(@PathVariable String data,  @PathVariable String startTime,@PathVariable String endTime) {
        LocalDate date = LocalDate.parse(data);
        LocalTime localStartTime = LocalTime.parse(startTime);
        LocalTime localEndTime = LocalTime.parse(endTime);
        List<CourtDTO> courtDTOS = reservationService.getAvailableCourts(date, localStartTime, localEndTime);
        return new ResponseEntity<>(courtDTOS, HttpStatus.OK);
    }


    @RequestMapping(value = "/updateUserList", method = RequestMethod.PUT)
    public ResponseEntity<ReservationDTO> addUserToReservation(@RequestBody AddUserRequest addUserRequest) {
        ReservationDTO reservationDTO = reservationService.joinRequest(addUserRequest);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getPartnerRequests/{startTime}/{endTime}")
    public GetPartnerRequestsResponse getPartnerRequests(@PathVariable String startTime, @PathVariable String endTime) {
        List<Reservation> reservationList;
        try {
            reservationList = reservationService.getPartnerRequests(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getPartnerRequests - Controller");

            return new GetPartnerRequestsResponse(null, "Reservations don't exist yet!");
        }

        return new GetPartnerRequestsResponse(reservationList, "Available requests");
    }

    @DeleteMapping(value = "/cancel/{reservationId}")
    public StatusResponse cancelReservation(@PathVariable String reservationId) {
        try {
            return reservationService.cancelReservation(reservationId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cancelReservation - Controller");
            return new StatusResponse("Failed to delete the reservation!");
        }
    }


    @PutMapping(value = "/findPlayers/{reservationId}")
    public FindPlayersResponse findPlayers(@PathVariable String reservationId) {
        try {
            Reservation reservation = reservationService.findPlayers(reservationId);
            if (reservation != null) {
                reservation.setStatus(0);

                return new FindPlayersResponse(reservation, "Reservation available to find players!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("findPlayers - Controller");
        }
        return new FindPlayersResponse(null, "Couldn't make reservation available!");
    }

    @GetMapping(value = "/getReservationByUsersIsContaining/{userId}")
    public List<Reservation> getReservationByUsersIsContaining(@PathVariable String userId) {
        List<Reservation> reservationList;
        try {
            reservationList = reservationService.getReservationByUsersIsContaining(userId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getReservationByUsersIsContaining - Controller");
            return null;
        }

        return reservationList;
    }


    @GetMapping(value = "/getReservationsByStatus/{reservationStatus}")
    public List<Reservation> getReservationByStatus(@PathVariable int reservationStatus) {
        List<Reservation> reservationList;
        try {
            reservationList = reservationService.getReservationByStatus(reservationStatus);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getReservationsByStatus - Controller");
            return null;
        }

        return reservationList;
    }
}
