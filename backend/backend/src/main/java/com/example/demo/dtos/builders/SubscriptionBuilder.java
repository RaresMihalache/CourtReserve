package com.example.demo.dtos.builders;

import com.example.demo.dtos.SubscriptionDTO;
import com.example.demo.model.Subscription;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubscriptionBuilder {
    public static SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        return new SubscriptionDTO(subscription.getId(), subscription.getReservations());
    }
}
