package org.ewallet.wallet.service.interfaces;

import org.ewallet.wallet.dto.WalletDto;

import java.util.List;

public interface WalletServiceInter {
    List<WalletDto> getAllWallets();
    WalletDto getWalletByUuid(String uuid);
    WalletDto getWalletByOwnerReference(String ownerReference);
    WalletDto saveWallet(WalletDto walletDto);
    WalletDto updateWallet(WalletDto walletDto);
}