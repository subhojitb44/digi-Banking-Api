package org.ewallet.authentication.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDto {
    private String uuid;
    private String name;
    private String balance;
    private String creationDate;
    private String ownerReference;
}