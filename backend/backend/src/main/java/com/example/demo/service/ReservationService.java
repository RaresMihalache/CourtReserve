package com.example.demo.service;

import com.example.demo.dtos.CourtDTO;
import com.example.demo.dtos.ReservationDTO;
import com.example.demo.dtos.builders.CourtBuilder;
import com.example.demo.dtos.builders.ReservationBuilder;
import com.example.demo.model.*;
import com.example.demo.model.request.AddUserRequest;
import com.example.demo.model.response.StatusResponse;
import com.example.demo.repository.CourtRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final EmailSenderService emailSenderService;

    private final SubscriptionService subscriptionService;

    private final CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, EmailSenderService emailSenderService, SubscriptionService subscriptionService, CourtRepository courtRepository) {
        this.reservationRepository = reservationRepository;
        this.emailSenderService = emailSenderService;
        this.subscriptionService = subscriptionService;
        this.courtRepository = courtRepository;
    }

    public ReservationDTO saveReservation(ReservationRequest reservationRequest) {

        Subscription subscription = new Subscription(reservationRequest.getSubscriptionId(), new ArrayList<>());
        reservationRequest.getReservation().setSubscription(subscription);
        Reservation reservation = new Reservation(reservationRequest.getReservation().getId(), 1,
                reservationRequest.getReservation().getStartTime(), reservationRequest.getReservation().getEndTime(),
                reservationRequest.getReservation().getCourt(), reservationRequest.getReservation().getUsers(),
                reservationRequest.getReservation().getSubscription());

//        emailSenderService.sendNotification(reservationRequest.getSendNotificationTo().getEmail(), "User " + reservationRequest.getReservation().getUsers().get(0).getEmail() + " send you a partner request!", "Join Request!");
        return ReservationBuilder.toReservationDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO getReservation(Long id) {

        Optional<Reservation> reservation = Optional.ofNullable(reservationRepository.getReservationById(id));
        ReservationDTO reservationDTO = ReservationBuilder.toReservationDTO(reservation.get());
        return reservationDTO;
    }

    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(ReservationBuilder::toReservationDTO)
                .collect(Collectors.toList());
    }

    public Reservation updateReservation(Reservation reservation) {

        Reservation reservationDB = reservationRepository.getReservationById(reservation.getId());

        if (Objects.nonNull(reservation.getStartTime())) {
            reservationDB.setStartTime(reservation.getStartTime());
        }

        if (Objects.nonNull(reservation.getEndTime())) {
            reservationDB.setEndTime(reservation.getEndTime());
        }

        if (Objects.nonNull(reservation.getCourt())) {
            reservationDB.setCourt(reservation.getCourt());
        }

        return reservationRepository.save(reservationDB);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<CourtDTO> getAvailableCourts(LocalDate localDate, LocalTime localStartTime, LocalTime localEndTime) {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Court> courts = courtRepository.findAll();
        for(Reservation reservation : reservations){
            LocalDate dateTime = LocalDate.parse(reservation.getStartTime().toString().substring(0, 10));
            LocalTime startTime = LocalTime.parse(reservation.getStartTime().toString().substring(11, 19));
            LocalTime endTime = LocalTime.parse(reservation.getEndTime().toString().substring(11, 19));

            if(localDate.isEqual(dateTime)){
                boolean overlap = overlappingTimes(localStartTime, localEndTime, startTime, endTime);
                if(overlap)
                    courts.remove(reservation.getCourt());
            }
        }
        return courts.stream().distinct().map(CourtBuilder::toCourtDTO)
                .collect(Collectors.toList());
    }

    public boolean overlappingTimes(LocalTime startTimeA, LocalTime endTimeA, LocalTime startTimeB, LocalTime endTimeB){
        return startTimeA.isBefore(endTimeB) && startTimeB.isBefore(endTimeA);
    }


    public ReservationDTO joinRequest(AddUserRequest addUserRequest) {

        Reservation reservationDB = reservationRepository.getReservationById(addUserRequest.getId());
        if (reservationDB.getStatus() == 0) {
            reservationDB.getUsers().clear();
            List<User> userList = reservationDB.getUsers();
            userList.add(addUserRequest.getUser());
            reservationDB.setStatus(1);
        } else if (reservationDB.getUsers().size() == 1 && reservationDB.getStatus() == 1) {
            List<User> userList = reservationDB.getUsers();
            userList.add(addUserRequest.getUser());
            reservationDB.setUsers(userList);
            reservationDB.setStatus(2);
        }

        reservationRepository.save(reservationDB);

        System.out.println(reservationDB.getUsers().size());
        return ReservationBuilder.toReservationDTO(reservationDB);
    }

    public List<Reservation> getPartnerRequests(String givenStartTime, String givenEndTime) {
        List<Reservation> filteredReservations = new ArrayList<>();

        try {
            List<Reservation> reservationList;
            reservationList = reservationRepository.findAll();
            for (Reservation reservation : reservationList) {
                if (reservation.getUsers().size() == 1 && reservation.getStatus() == 1) {
                    String startTime = reservation.getStartTime().toString();
                    startTime = startTime.replace('T', ' ');
                    String endTime = reservation.getEndTime().toString();
                    endTime = endTime.replace('T', ' ');

                    if (startTime.equals(givenStartTime) && endTime.equals(givenEndTime)) {
                        filteredReservations.add(reservation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getPartnerRequests - Service");
            return null;
        }

        return filteredReservations;
    }

    public StatusResponse cancelReservation(String id) {
        Reservation reservation = reservationRepository.getReservationById(Long.parseLong(id));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String nowString = formatter.format(now);
        nowString = nowString.replace('T', ' ');
        now = LocalDateTime.parse(nowString, formatter);

        String startTime = reservation.getStartTime().toString();
        LocalDateTime dateTime = LocalDateTime.parse(startTime);
        long time = ChronoUnit.MINUTES.between(now, dateTime);
        time = Math.round(time / 60.0);
        System.out.println(time);
        try {
            if (time >= 24) {
                reservationRepository.deleteReservationById(Long.parseLong(id));
                return new StatusResponse("Reservation  successfully deleted!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deleteById - Service");
        }
        reservationRepository.deleteReservationById(Long.parseLong(id));
        if(reservation.getSubscription().getReservations().size() == 1){
            this.subscriptionService.deleteSubscription(reservation.getSubscription().getId());
        }

        return new StatusResponse("Reservation  successfully deleted! You'll be punished");
    }


    public Reservation findPlayers(String reservationId) {
        Reservation reservation = reservationRepository.getReservationById(Long.parseLong(reservationId));
        if (reservation != null) {
            reservation.setStatus(0);
            try {
                reservationRepository.save(reservation);
                List<User> userList = userRepository.findAll();
                userList.remove(reservation.getUsers().get(0));
                for (User user : userList) {
                    emailSenderService.sendNotification(user.getEmail(), "You receive a takeover request. Please checkout the platform and join if you want to!", "Takeover request!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("findPlayers - Service");
            }

            return reservation;
        }

        return null;
    }

    public List<Reservation> getReservationByUsersIsContaining(String userId) {
        List<Reservation> reservationList = new ArrayList<>();
        User user = userRepository.findUserById(Long.parseLong(userId));
        if (user != null) {
            try {
                reservationList = reservationRepository.getReservationByUsersIsContaining(user);
                reservationList.removeIf(reservation -> reservation.getStatus() == 1);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("getReservationByUsersIsContaining - Service");
                return null;
            }
        }

        return reservationList;
    }

    public List<Reservation> getReservationByStatus(int status) {
        List<Reservation> reservationList;

        try {
            reservationList = reservationRepository.getReservationByStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getReservationsByStatus - Service");
            return null;
        }

        return reservationList;
    }

}
