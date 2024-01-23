package org.ewallet.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ewallet.transaction.enums.TransactionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter @Setter @ToString
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "uuid", nullable = false)
    private String uuid;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "operator_reference", nullable = false)
    private String operatorReference;
}