package com.example.demo.controller;

import com.example.demo.dtos.TariffDTO;
import com.example.demo.model.Tariff;
import com.example.demo.service.TariffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tariff")
public class TariffController {

    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ResponseEntity<TariffDTO> save(@RequestBody Tariff tariff){
        TariffDTO tariffDTO = tariffService.saveTariff(tariff);
        return new ResponseEntity<>(tariffDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateTariff(@RequestBody Tariff tariff){
        tariffService.updateTariff(tariff);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
