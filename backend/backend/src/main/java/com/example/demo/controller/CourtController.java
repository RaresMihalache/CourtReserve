package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.dtos.CourtDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Court;
import com.example.demo.model.Tariff;
import com.example.demo.service.CourtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/court")
public class CourtController {

    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @RequestMapping(value="/save",method= RequestMethod.POST)
    public ResponseEntity<CourtDTO> save(@RequestBody Court court){
        CourtDTO courtDTO = courtService.saveCourt(court);
        return new ResponseEntity<>(courtDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/getCourt/{id}",method= RequestMethod.GET)
    public ResponseEntity<CourtDTO> getCourt(@PathVariable Long id){
        CourtDTO courtDTO = courtService.getCourt(id);
        return new ResponseEntity<>(courtDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/findAll",method = RequestMethod.GET)
    public ResponseEntity<List<CourtDTO>> findAllCourts(){
        List<CourtDTO> courtDTOS = courtService.findAll();
        return new ResponseEntity<>(courtDTOS,HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ResponseEntity updateCourt(@RequestBody Court court){
        courtService.updateCourt(court);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/delete/{id}",method = {RequestMethod.DELETE})
    public ResponseEntity deleteCourt(@PathVariable Long id){
        courtService.deleteCourt(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/findAllByAddress",method = RequestMethod.GET)
    public ResponseEntity<List<CourtDTO>> findAllCourtsByAddress(@RequestBody Address address){
        List<CourtDTO> courtDTOS = courtService.getCourtsByAddress(address);
        return new ResponseEntity<>(courtDTOS,HttpStatus.OK);
    }

    @RequestMapping(value="/getCourtTariff/{id}",method= RequestMethod.GET)
    public ResponseEntity<Tariff> getTariff(@PathVariable Long id){
        Tariff courtTariff = courtService.getCourtTariff(id);
        return new ResponseEntity<>(courtTariff, HttpStatus.OK);
    }

}
