package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;
import java.util.ArrayList;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="address")
public class Address {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "city")
    private String city;

    @JsonManagedReference
    @OneToMany(mappedBy="address")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Court> courts = new ArrayList<>();

}
