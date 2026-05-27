package com.bank.transactionservice.repository;

import com.bank.transactionservice.entity.Transaction;
import com.bank.transactionservice.entity.TransactionStatus;
import com.bank.transactionservice.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>
{

    Optional<Transaction> findByTransactionReference(String transactionReference);

    boolean existsByTransactionReference(String transactionReference);

    List<Transaction> findByInitiatedBy(String initiatedBy);

    List<Transaction> findByFromAccountNumber(String fromAccountNumber);

    List<Transaction> findByToAccountNumber(String toAccountNumber);

    List<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);

    List<Transaction> findByTransactionType(TransactionType transactionType);

}
