package com.bank.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loginTime;
    private String ipAddress;
    private String deviceInfo;
    private String loginStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
