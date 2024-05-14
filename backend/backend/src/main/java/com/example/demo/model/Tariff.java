package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tariff")
public class Tariff {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "season")
    private String season;

    @Column(name = "nightTariff")
    private Float nightTariff;

    @Column(name="dayOfTheWeek")
    private String dayOfTheWeek;

    @Column(name="timeOfTheDay")
    private String timeOfTheDay;

}
