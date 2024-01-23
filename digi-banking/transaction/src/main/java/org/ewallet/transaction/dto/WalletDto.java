package org.ewallet.transaction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class WalletDto {
    private String uuid ;
    private String name ;
    private String balance ;
    private String creationDate ;
    private String ownerReference;
}