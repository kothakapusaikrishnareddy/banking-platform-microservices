package com.bank.accountservice.service;

import com.bank.accountservice.dto.request.CreateBankAccountRequest;
import com.bank.accountservice.dto.response.BalanceResponse;
import com.bank.accountservice.dto.response.BankAccountResponse;
import com.bank.accountservice.exception.UnauthorizedAccountAccessException;

import java.util.List;

public interface BankAccountService {

    BankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest, String email);
    List<BankAccountResponse> getMyAccounts(String email);

    BankAccountResponse getAccountDetails(String accountNumber, String customerEmail) throws UnauthorizedAccountAccessException;
    BalanceResponse  getBalance(String accountNumber,  String customerEmail);
}
