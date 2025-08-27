package com.mlbyl.bankingproject.dto.Account_Dto.request;

import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequest {
    @NotNull(message = "Currency cannot be null")
    @Schema(allowableValues = {"AZN", "TRY", "USD", "GBP", "EUR"})
    private Currency currency;

    @NotNull(message = "Account type cannot be null")
    @Schema(allowableValues = {"SAVINGS","CHECKING","CREDIT","LOAN"})
    private AccountType accountType;
}
