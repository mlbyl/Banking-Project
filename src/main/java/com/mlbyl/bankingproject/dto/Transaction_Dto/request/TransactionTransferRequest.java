package com.mlbyl.bankingproject.dto.Transaction_Dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTransferRequest {
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "iban cannot be null")
    private String senderIBAN;

    @NotNull(message = "iban cannot be null")
    private String recieverIBAN;


}
