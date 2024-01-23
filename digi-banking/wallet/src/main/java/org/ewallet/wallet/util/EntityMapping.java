package org.ewallet.wallet.util;

import org.ewallet.wallet.dto.WalletDto;
import org.ewallet.wallet.entity.Wallet;
import org.modelmapper.ModelMapper;

public class EntityMapping{
    private static final ModelMapper modelMapper = new ModelMapper();


    private EntityMapping(){}
    public static Wallet walletDtoToWallet(WalletDto walletDto){
        return modelMapper.map(walletDto, Wallet.class);
    }
    public static WalletDto walletToWalletDto(Wallet wallet){
        return modelMapper.map(wallet, WalletDto.class);
    }
}