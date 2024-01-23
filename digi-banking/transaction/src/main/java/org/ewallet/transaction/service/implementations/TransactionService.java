package org.ewallet.transaction.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ewallet.transaction.client.apis.WalletApis;
import org.ewallet.transaction.dto.TransactionDto;
import org.ewallet.transaction.dto.WalletDto;
import org.ewallet.transaction.entity.Transaction;
import org.ewallet.transaction.enums.TransactionType;
import org.ewallet.transaction.repository.TransactionRepository;
import org.ewallet.transaction.service.interfaces.TransactionServiceInter;
import org.ewallet.transaction.util.EntityMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionService implements TransactionServiceInter {
    private final TransactionRepository transactionRepository;
    private final WalletApis walletApis;

    @Override @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = EntityMapping.transactionDtoToTransaction(transactionDto);
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setCreatedAt(LocalDateTime.now(ZoneId.of("Africa/Casablanca")));

        try {
            transactionRepository.save(transaction);
        } catch (Exception e){
            return null;
        }

        return EntityMapping.transactionToTransactionDto(transaction);
    }

    private boolean deposit(WalletDto walletDto, Double amount) {
        TransactionDto transaction = forwardToCreateTransactionMethod(walletDto,amount,TransactionType.DEPOSIT);

        log.info("check pushed transaction data {}",transaction);

        if(transaction == null)
            return false;

        Double balance = amount + Double.parseDouble(walletDto.getBalance());

        return updateWalletBalance(walletDto,balance);
    }
    private boolean withdrawal(WalletDto walletDto, Double amount) {
        boolean isAllowedToBeWithdrawn = checkIfBalanceGreaterOrEqualGivenAmount(walletDto,amount);
        if( isAllowedToBeWithdrawn ){
            TransactionDto transaction = forwardToCreateTransactionMethod(walletDto,amount,TransactionType.WITHDRAWAL);
            log.info("check pushed transaction data {}",transaction);

            if(transaction == null)
                return false;

            Double balance =  Double.parseDouble(walletDto.getBalance()) - amount;

            return updateWalletBalance(walletDto,balance);
        } else
            return false;
    }
    private boolean updateWalletBalance(WalletDto walletDto, Double balance){
        walletDto.setBalance(Double.toString(balance));

        log.info("Updated Balance Wallet : {}",walletDto);
        try {
            if(walletApis.updateWalletBalanceApi(walletDto).getStatusCode().toString().startsWith("200"))
                log.info("Updated Balance Wallet : {}",walletApis.updateWalletBalanceApi(walletDto).getBody());
        } catch (Exception e){
            return false;
        }
        return true;
    }
    private TransactionDto forwardToCreateTransactionMethod(WalletDto walletDto , Double amount , TransactionType transactionType){
        return createTransaction(TransactionDto
                .builder()
                .type(transactionType.toString())
                .amount(Double.toString(amount))
                .operatorReference(walletDto.getOwnerReference())
                .build());
    }
    @Override
    public String makeOperation(String operationType, TransactionDto transactionDto) {
        try {
            if( walletApis.getSingleWalletByOwnerReferenceApi(transactionDto.getOperatorReference()).getStatusCode().toString().startsWith("200") ){
                WalletDto walletDto = EntityMapping
                        .objectToWalletDto(walletApis.getSingleWalletByOwnerReferenceApi(transactionDto.getOperatorReference()).getBody());
                log.info("from test {}",walletDto);
                Double amount = isAmountValid(transactionDto.getAmount());

                if(amount != null){
                    operationType = operationType.toLowerCase();

                    boolean isOperationSucceed = false;

                    if(operationType.equals("deposit"))
                        isOperationSucceed = deposit(walletDto, amount);
                    else if(operationType.equals("withdrawal"))
                        isOperationSucceed = withdrawal(walletDto,amount);


                    return isOperationSucceed
                            ? "201 The " + operationType.toLowerCase() + " Operation Was Successfully Made"
                            : "500 The Operation Didn't Complete Something Went Wrong";
                } else
                    return "404 The Provided Amount Does Not exist";
            }
        } catch (Exception e){
            return "500 The Provided Owner Reference Does Not Exist Or Something Went Wrong At Server Side Level";
        }
        return "";
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(EntityMapping::transactionToTransactionDto)
                .toList();
    }

    @Override
    public TransactionDto getTransactionByReference(String reference) {
        return EntityMapping.transactionToTransactionDto(transactionRepository.findByUuid(reference).get());
    }

    private boolean checkIfBalanceGreaterOrEqualGivenAmount(WalletDto wallet, Double amount) {
            return Double.parseDouble(wallet.getBalance()) >= amount;
        }

    private Double isAmountValid(String amount){
        if(Pattern.matches("^0+$",amount))
                return null;
        try {
            return Double.parseDouble(amount);
        }catch (Exception e){
            return null;
        }
    }
}