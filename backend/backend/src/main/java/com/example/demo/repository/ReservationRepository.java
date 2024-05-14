package com.example.demo.repository;

import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Reservation getReservationById(Long id);
    void deleteById(Long id);
    Long deleteReservationById(Long id);
    List<Reservation> getReservationByUsersIsContaining(User user);
    List<Reservation> getReservationByStatus(int status);
}
