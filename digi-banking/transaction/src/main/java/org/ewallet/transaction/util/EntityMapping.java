package org.ewallet.transaction.util;

import org.ewallet.transaction.dto.TransactionDto;
import org.ewallet.transaction.dto.WalletDto;
import org.ewallet.transaction.entity.Transaction;
import org.modelmapper.ModelMapper;

public class EntityMapping {
    private static final ModelMapper modelMapper = new ModelMapper();

    private EntityMapping(){}

    public static TransactionDto transactionToTransactionDto(Transaction transaction){
        return modelMapper.map(transaction, TransactionDto.class);
    }
    public static Transaction transactionDtoToTransaction(TransactionDto transactionDto){
        return modelMapper.map(transactionDto, Transaction.class);
    }

    public static WalletDto objectToWalletDto(Object object){
        return modelMapper.map(object, WalletDto.class);
    }
}