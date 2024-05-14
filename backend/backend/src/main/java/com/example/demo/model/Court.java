package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="court")
@AllArgsConstructor
public class Court {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "surface")
    private String surface;

    @Column(name = "status")
    private String status;

    @OneToOne
    private Tariff tariff;

    @JsonBackReference
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="address",nullable=false)
    private Address address;


    @JsonManagedReference
    @OneToMany(mappedBy="court")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reservation> reservations = new ArrayList<>();

}
