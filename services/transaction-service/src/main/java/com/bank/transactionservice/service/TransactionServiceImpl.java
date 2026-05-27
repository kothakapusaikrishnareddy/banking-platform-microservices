package com.bank.transactionservice.service;

import com.bank.transactionservice.dto.request.TransferRequest;
import com.bank.transactionservice.dto.response.TransactionResponse;
import com.bank.transactionservice.entity.Transaction;
import com.bank.transactionservice.entity.TransactionStatus;
import com.bank.transactionservice.entity.TransactionType;
import com.bank.transactionservice.exception.TransactionFailedException;
import com.bank.transactionservice.repository.TransactionRepository;
import com.bank.transactionservice.util.TransactionReferenceGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;

    private TransactionReferenceGenerator  transactionReferenceGenerator;

    private static final String ACCOUNT_SERVICE_BASE_URL =
            "http://localhost:8083/api/v1/internal/accounts";


    @Override
    @Transactional
    public TransactionResponse transferMoney(TransferRequest transferRequest, String initiatedBy) {

        try{
            String transactionReference = generateUniqueTransactionReference();

            callDebitApi(
                    transferRequest.getFromAccountNumber(),
                    transferRequest.getAmount()
            );

            callCreditApi(
                    transferRequest.getToAccountNumber(),
                    transferRequest.getAmount()
            );

            Transaction transaction = Transaction.builder()
                    .transactionReference(transactionReference)
                    .fromAccountNumber(transferRequest.getFromAccountNumber())
                    .toAccountNumber(transferRequest.getToAccountNumber())
                    .amount(transferRequest.getAmount())
                    .currency(transferRequest.getCurrency())
                    .transactionType(TransactionType.TRANSFER)
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .remarks(transferRequest.getRemarks())
                    .initiatedBy(initiatedBy)
                    .createdAt(LocalDateTime.now())
                    .build();
            Transaction savedTransaction = transactionRepository.save(transaction);

            return mapToResponse(savedTransaction);
        } catch (Exception e) {
            throw new TransactionFailedException("Money tranfer failed: " + e.getMessage());
        }
    }

    @Override
    public List<TransactionResponse> getMyTransactions(String initiatedBy) {
        return transactionRepository
                .findByInitiatedBy(initiatedBy)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void callDebitApi(String accountNumber, java.math.BigDecimal amount) {
        String url = ACCOUNT_SERVICE_BASE_URL + "/debit";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        String requestBody = """
               {
                    "accountNumber": "%s",
                    "amount": "%s"
               }
               """.formatted(accountNumber,amount);

        HttpEntity<String> requestEntity =
                new HttpEntity<>(
                        requestBody,
                        headers
                );

        restTemplate.postForEntity(
                url,
                requestEntity,
                String.class
        );

    }

    private void callCreditApi(String accountNumber, java.math.BigDecimal amount) {
        String url = ACCOUNT_SERVICE_BASE_URL + "/credit";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        String requestBody = """
                {
                    "accountNumber": "%s",
                    "amount": "%s"
                }
        """.formatted(accountNumber,amount);

        restTemplate.postForEntity(
                url,
                requestBody,
                String.class
        );
    }

    private String generateUniqueTransactionReference() {

        String reference;

        do{
            reference=transactionReferenceGenerator.generateTransactionReference();
        }while(transactionRepository.existsByTransactionReference(reference));

        return reference;
    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .transactionReference(
                        transaction.getTransactionReference()
                )
                .fromAccountNumber(
                        transaction.getFromAccountNumber()
                )
                .toAccountNumber(
                        transaction.getToAccountNumber()
                )
                .amount(
                        transaction.getAmount()
                )
                .currency(
                        transaction.getCurrency()
                )
                .transactionType(
                        transaction.getTransactionType()
                )
                .transactionStatus(
                        transaction.getTransactionStatus()
                )
                .remarks(
                        transaction.getRemarks()
                )
                .initiatedBy(
                        transaction.getInitiatedBy()
                )
                .createdAt(
                        transaction.getCreatedAt()
                )
                .build();
    }
}
