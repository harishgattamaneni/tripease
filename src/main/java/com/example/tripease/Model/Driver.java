package com.example.tripease.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;


    private String name;
    private int age;

    @Column(unique = true,nullable = false)
    private String emailId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="driverId")
    List<Booking> bookings = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cab_id")
    Cab cab;
}
