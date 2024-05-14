package com.example.demo.service;

import com.example.demo.dtos.TariffDTO;
import com.example.demo.dtos.builders.TariffBuilder;
import com.example.demo.model.Tariff;
import com.example.demo.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public TariffDTO saveTariff(Tariff tariff) {
        return TariffBuilder.toTariffDTO(tariffRepository.save(tariff));
    }

    public TariffDTO updateTariff(Tariff tariff){
        Tariff tariffDB = tariffRepository.getById(tariff.getId());

        if(Objects.nonNull(tariff.getNightTariff())){
            tariffDB.setNightTariff(tariff.getNightTariff());
        }

        return TariffBuilder.toTariffDTO(tariffRepository.save(tariffDB));
    }
}
