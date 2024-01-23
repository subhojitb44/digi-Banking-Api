package org.ewallet.transaction.service.interfaces;

import org.ewallet.transaction.dto.TransactionDto;

import java.util.List;

public interface TransactionServiceInter {
    TransactionDto createTransaction(TransactionDto transactionDto);
    String makeOperation(String operationType, TransactionDto walletDto);
    List<TransactionDto> getAllTransactions();
    TransactionDto getTransactionByReference(String reference);
}