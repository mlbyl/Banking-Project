package com.mlbyl.bankingproject.dto.Account_Dto.response;

import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String IBAN;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Currency currency;
    private BigDecimal balance;
    private LocalDateTime lastActivityDate;
    private UUID userId;
//    private UserInAccountResponse user;
}
