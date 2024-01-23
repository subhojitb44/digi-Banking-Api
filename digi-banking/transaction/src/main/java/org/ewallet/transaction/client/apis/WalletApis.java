package org.ewallet.transaction.client.apis;

import org.ewallet.transaction.dto.WalletDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ewallet-wallet-service-v1")
public interface WalletApis {
    @GetMapping("wallet/api/v1/fetching/all")
    ResponseEntity<List<WalletDto>> getAllWalletsApi();
    @GetMapping("wallet/api/v1/fetching/single/ref/uuid/{uuid}")
    ResponseEntity<Object> getSingleWalletByReferenceApi(@PathVariable("uuid") String reference);
    @GetMapping("wallet/api/v1/fetching/single/ref/owner/{ownerId}")
    ResponseEntity<Object> getSingleWalletByOwnerReferenceApi(@PathVariable("ownerId") String reference);
    @PostMapping("wallet/api/v1/adding")
    ResponseEntity<Object> createWalletApi(@RequestBody WalletDto walletDto);
    @PutMapping("wallet/api/v1/updating/balance")
    ResponseEntity<WalletDto> updateWalletBalanceApi(@RequestBody WalletDto walletDto);
}