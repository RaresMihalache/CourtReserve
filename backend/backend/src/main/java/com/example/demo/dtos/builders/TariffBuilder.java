package com.example.demo.dtos.builders;

import com.example.demo.dtos.SubscriptionDTO;
import com.example.demo.dtos.TariffDTO;
import com.example.demo.model.Tariff;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TariffBuilder {
    public static TariffDTO toTariffDTO(Tariff tariff) {
        return new TariffDTO(tariff.getId(), tariff.getNightTariff());
    }
}
