package com.bank.customerservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true, length = 12)
    private String aadhaarNumber;
    @Column(unique = true, length = 10)
    private String panNumber;
    private String occupation;
    @Column(precision = 15, scale = 2)
    private BigDecimal annualIncome;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;
    @Column(nullable = false, length = 10)
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(
            mappedBy = "customerProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses;
}
