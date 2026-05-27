package com.bank.accountservice.service;

import com.bank.accountservice.dto.request.CreateBankAccountRequest;
import com.bank.accountservice.dto.request.CreditRequest;
import com.bank.accountservice.dto.request.DebitRequest;
import com.bank.accountservice.dto.response.BalanceResponse;
import com.bank.accountservice.dto.response.BankAccountResponse;
import com.bank.accountservice.entity.AccountStatus;
import com.bank.accountservice.entity.BankAccount;
import com.bank.accountservice.exception.AccountNotFoundException;
import com.bank.accountservice.exception.InsufficientBalanceException;
import com.bank.accountservice.exception.UnauthorizedAccountAccessException;
import com.bank.accountservice.repository.BankAccountRepository;
import com.bank.accountservice.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final AccountNumberGenerator  accountNumberGenerator;

    @Override
    public BankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest, String email) {

        String accountNumber = generateUniqueAccountNumber();

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(accountNumber)
                .customerEmail(email)
                .accountType(createBankAccountRequest.getAccountType())
                .balance(BigDecimal.ZERO)
                .currency(createBankAccountRequest.getCurrency())
                .accountStatus(AccountStatus.ACTIVE)
                .branchCode(createBankAccountRequest.getBranchCode())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);


        return mapToResponse(savedAccount);
    }



    @Override
    public List<BankAccountResponse> getMyAccounts(String email) {
        return bankAccountRepository
                .findByCustomerEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BankAccountResponse getAccountDetails(
            String accountNumber,
            String customerEmail
    ) throws UnauthorizedAccountAccessException {

        BankAccount bankAccount =
                bankAccountRepository
                        .findByAccountNumber(accountNumber)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found"
                                )
                        );

        // Ownership validation
        if (!bankAccount.getCustomerEmail()
                .equals(customerEmail)) {

            throw new UnauthorizedAccountAccessException(
                    "You are not authorized to access this account"
            );
        }

        return mapToResponse(bankAccount);
    }

    @Override
    public BalanceResponse getBalance(String accountNumber, String customerEmail) {

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()->new AccountNotFoundException("Account not found"));
        BalanceResponse response = BalanceResponse.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .currency(bankAccount.getCurrency())
                .build();

        if(!bankAccount.getCustomerEmail().equals(customerEmail)){
            throw new UnauthorizedAccountAccessException("You are not authorized to perform this operation");
        }

        return response;
    }

    @Override
    public void debitAccount(
            DebitRequest request
    ) {

        BankAccount bankAccount =
                bankAccountRepository
                        .findByAccountNumber(
                                request.getAccountNumber()
                        )
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found"
                                )
                        );

        // Check sufficient balance
        if (bankAccount.getBalance()
                .compareTo(request.getAmount()) < 0) {

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        // Deduct balance
        bankAccount.setBalance(
                bankAccount.getBalance()
                        .subtract(request.getAmount())
        );

        bankAccount.setUpdatedAt(
                LocalDateTime.now()
        );

        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void creditAccount(
            CreditRequest request
    ) {

        BankAccount bankAccount =
                bankAccountRepository
                        .findByAccountNumber(
                                request.getAccountNumber()
                        )
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found"
                                )
                        );

        // Add balance
        bankAccount.setBalance(
                bankAccount.getBalance()
                        .add(request.getAmount())
        );

        bankAccount.setUpdatedAt(
                LocalDateTime.now()
        );

        bankAccountRepository.save(bankAccount);
    }

    public String generateUniqueAccountNumber() {
        String accountNumber;

        do {
            accountNumber = accountNumberGenerator.generateAccountNumber();

        }while(bankAccountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    public BankAccountResponse mapToResponse(BankAccount savedAccount) {
        return BankAccountResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .customerEmail(savedAccount.getCustomerEmail())
                .accountType(savedAccount.getAccountType())
                .balance(savedAccount.getBalance())
                .currency(savedAccount.getCurrency())
                .accountStatus(savedAccount.getAccountStatus())
                .branchCode(savedAccount.getBranchCode())
                .createdAt(savedAccount.getCreatedAt())
                .build();
    }
}
