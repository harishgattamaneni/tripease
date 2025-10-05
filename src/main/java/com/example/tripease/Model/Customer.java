package com.example.tripease.Model;

import com.example.tripease.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customer_info")
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(name="first_name")
    private String name;
    private int age;

    @Column(unique = true,nullable = false)
    private String emailId;

    @Enumerated(value=EnumType.STRING)
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    List<Booking> bookings = new ArrayList<>();

}
