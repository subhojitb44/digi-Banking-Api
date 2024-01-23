package org.ewallet.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.ewallet.wallet.dto.WalletDto;
import org.ewallet.wallet.service.interfaces.WalletServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet/api/v1")
@RequiredArgsConstructor
public class WalletController{
    private final WalletServiceInter walletService;

    @GetMapping("/fetching/all")
    ResponseEntity<List<WalletDto>> getAllWalletsApi(){
        List<WalletDto> walletDtos = walletService.getAllWallets();

        return ( ! walletDtos.isEmpty() )
                ? ResponseEntity.ok(walletDtos)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/fetching/single/ref/uuid/{uuid}")
    ResponseEntity<Object> getSingleWalletByReferenceApi(@PathVariable("uuid") String reference){
        WalletDto wallet = walletService.getWalletByUuid(reference);

        return (wallet != null) ? ResponseEntity.ok(wallet) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no wallet with this uuid reference");
    }
    @GetMapping("/fetching/single/ref/owner/{ownerId}")
    ResponseEntity<Object> getSingleWalletByOwnerReferenceApi(@PathVariable("ownerId") String reference){
        WalletDto wallet = walletService.getWalletByOwnerReference(reference);

        return (wallet != null) ? ResponseEntity.ok(wallet) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no wallet with this owner reference");
    }
    @PostMapping("/adding")
    ResponseEntity<Object> createWalletApi(@RequestBody WalletDto walletDto){
        WalletDto wallet = walletService.saveWallet(walletDto);
        return (wallet != null)
                ? ResponseEntity.ok(wallet)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Wallet wasn't saved something went wrong please retry");
    }
    @PutMapping("/updating/balance")
    ResponseEntity<Object> updateWalletBalanceApi(@RequestBody WalletDto walletDto){
        WalletDto wallet = walletService.updateWallet(walletDto);
        return (wallet != null)
                ? ResponseEntity.ok(wallet)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Wallet wasn't updated please try again");
    }
}
