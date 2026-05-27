package com.bank.transactionservice.service;

import com.bank.transactionservice.dto.request.TransferRequest;
import com.bank.transactionservice.dto.response.TransactionResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface TransactionService {


    TransactionResponse transferMoney(TransferRequest transferRequest, String initiatedBy);

    List<TransactionResponse> getMyTransactions(String initiatedBy);


}
