package com.mlbyl.bankingproject.entity;


import com.mlbyl.bankingproject.entity.enums.TransactionStatus;
import com.mlbyl.bankingproject.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id",nullable = true)
    @ToString.Exclude
    private Account toAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id",nullable = true)
    @ToString.Exclude
    private Account fromAccount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
