package org.ewallet.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "wallet")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter @ToString
public class Wallet {
    @Id
    private String id;
    private String uuid;
    private String name;
    private Double balance;
    private LocalDateTime creationDate;
    private String ownerReference;
}