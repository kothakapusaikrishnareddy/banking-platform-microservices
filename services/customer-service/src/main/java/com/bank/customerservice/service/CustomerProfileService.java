package com.bank.customerservice.service;

import com.bank.customerservice.dto.request.CreateCustomerProfileRequest;
import com.bank.customerservice.dto.response.CustomerProfileResponse;
import com.bank.customerservice.exception.*;

import java.util.List;

public interface CustomerProfileService {

    CustomerProfileResponse createCustomerProfile(CreateCustomerProfileRequest request, String email) throws CustomerProfileAlreadyExistsException, AadhaarAlreadyExistsException, PanAlreadyExistsException;
    CustomerProfileResponse getCustomerProfile(String email) throws CustomerNotFoundException;
    List<CustomerProfileResponse> getAllCustomers();
}
