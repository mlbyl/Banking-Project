package com.mlbyl.bankingproject.dto.Transaction_Dto.response;

import com.mlbyl.bankingproject.entity.enums.TransactionStatus;
import com.mlbyl.bankingproject.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private Long senderAccountId;
    private Long recieverAccountId;
}
