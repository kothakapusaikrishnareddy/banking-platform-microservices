package com.bank.customerservice.service;

import com.bank.customerservice.dto.request.AddressRequest;
import com.bank.customerservice.dto.request.CreateCustomerProfileRequest;
import com.bank.customerservice.dto.response.AddressResponse;
import com.bank.customerservice.dto.response.CustomerProfileResponse;
import com.bank.customerservice.entity.Address;
import com.bank.customerservice.entity.CustomerProfile;
import com.bank.customerservice.entity.KycStatus;
import com.bank.customerservice.exception.*;
import com.bank.customerservice.repository.AddressRepository;
import com.bank.customerservice.repository.CustomerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InvalidClassException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService{

    private final CustomerProfileRepository customerProfileRepository;
    private final AddressRepository addressRepository;

    @Override
    public CustomerProfileResponse createCustomerProfile(CreateCustomerProfileRequest request, String email) throws CustomerProfileAlreadyExistsException,AadhaarAlreadyExistsException,PanAlreadyExistsException {

        if(customerProfileRepository.existsByEmail(email)){
            throw new CustomerProfileAlreadyExistsException("Customer Profile already exists");
        }

        if(customerProfileRepository.existsByAadhaarNumber(request.getAadhaarNumber())){
            throw new AadhaarAlreadyExistsException("This Aadhaar Number linked to Customer Profile already exists");
        }

        if(customerProfileRepository.existsByPanNumber(request.getPanNumber())){
            throw new PanAlreadyExistsException("This Pan Number linked to Customer Profile already exists");
        }


        CustomerProfile customerProfile = CustomerProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .occupation(request.getOccupation())
                .annualIncome(request.getAnnualIncome())
                .maritalStatus(request.getMaritalStatus())
                .kycStatus(KycStatus.PENDING)
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        List<Address> addresses =
                request.getAddresses()
                        .stream()
                        .map(addressRequest ->
                                mapToAddressEntity(
                                        addressRequest,
                                        customerProfile
                                )
                        )
                        .toList();

        customerProfile.setAddresses(addresses);
        CustomerProfile savedCustomer = customerProfileRepository.save(customerProfile);
        return mapToCustomerProfileResponse(savedCustomer);
    }

    @Override
    public CustomerProfileResponse getCustomerProfile(String email) throws CustomerNotFoundException {

        CustomerProfile customerProfile = customerProfileRepository.findByEmail(email)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found"));

        return mapToCustomerProfileResponse(customerProfile);
    }

    private Address mapToAddressEntity(
            AddressRequest request,
            CustomerProfile customerProfile
    ) {

        return Address.builder()
                .addressType(request.getAddressType())
                .houseNumber(request.getHouseNumber())
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())
                .createdAt(LocalDateTime.now())
                .customerProfile(customerProfile)
                .build();
    }

    private CustomerProfileResponse mapToCustomerProfileResponse(
            CustomerProfile customerProfile
    ) {

        List<AddressResponse> addressResponses =
                customerProfile.getAddresses()
                        .stream()
                        .map(address -> AddressResponse.builder()
                                .id(address.getId())
                                .addressType(address.getAddressType())
                                .city(address.getCity())
                                .state(address.getState())
                                .country(address.getCountry())
                                .pincode(address.getPincode())
                                .build())
                        .toList();

        return CustomerProfileResponse.builder()
                .id(customerProfile.getId())
                .email(customerProfile.getEmail())
                .fullName(
                        customerProfile.getFirstName()
                                + " "
                                + customerProfile.getLastName()
                )
                .kycStatus(customerProfile.getKycStatus())
                .createdAt(customerProfile.getCreatedAt())
                .addresses(addressResponses)
                .build();
    }

    @Override
    public List<CustomerProfileResponse> getAllCustomers() {

        return customerProfileRepository
                .findAll()
                .stream()
                .map(this::mapToCustomerProfileResponse)
                .toList();
    }
}
