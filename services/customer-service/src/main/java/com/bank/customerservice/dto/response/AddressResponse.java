package com.bank.customerservice.dto.response;

import com.bank.customerservice.entity.AddressType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private Long id;

    private AddressType addressType;

    private String city;

    private String state;

    private String country;

    private String pincode;
}