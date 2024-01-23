package org.ewallet.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.ewallet.transaction.dto.TransactionDto;
import org.ewallet.transaction.service.interfaces.TransactionServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction/api/v1")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceInter transactionService;

    @GetMapping("/fetching/single/{transactionReference}")
    public ResponseEntity<TransactionDto> getTransactionByReferenceApi(@PathVariable String transactionReference){
        TransactionDto transactionDto = transactionService.getTransactionByReference(transactionReference);
        return ( transactionDto != null )
                ? ResponseEntity.ok(transactionDto)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/fetching/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactionsApi(){
        List<TransactionDto> transactionsList = transactionService.getAllTransactions();
        return ( transactionsList.isEmpty() )
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(transactionsList);
    }
    @PostMapping("/operation/{operationType}") @Transactional
    public ResponseEntity<String> makeAddOrSubtractOperation(@PathVariable("operationType") String operationType
            , @RequestBody TransactionDto transactionDto){

        String responseStatusCode = transactionService.makeOperation(operationType,transactionDto);
        String message = responseStatusCode.substring(4);

        if (responseStatusCode.startsWith("201"))
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
         else if(responseStatusCode.startsWith("404"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
         else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}