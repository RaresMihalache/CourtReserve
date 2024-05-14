package com.example.demo.repository;

import com.example.demo.model.Court;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    Court getCourtById(Long id);
    void deleteCourtById(Long id);
}
