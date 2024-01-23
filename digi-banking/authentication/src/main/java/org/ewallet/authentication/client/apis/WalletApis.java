package org.ewallet.authentication.client.apis;

import org.ewallet.authentication.dto.WalletDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ewallet-wallet-service-v1")
public interface WalletApis {
    @PostMapping("wallet/api/v1/adding")
    ResponseEntity<Object> createWalletApi(@RequestBody WalletDto walletDto);
}