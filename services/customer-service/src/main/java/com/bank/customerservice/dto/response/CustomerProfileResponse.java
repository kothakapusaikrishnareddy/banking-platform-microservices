package com.bank.customerservice.dto.response;

import com.bank.customerservice.entity.KycStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerProfileResponse {

    private Long id;

    private String email;

    private String fullName;

    private KycStatus kycStatus;

    private LocalDateTime createdAt;

    private List<AddressResponse> addresses;
}