package com.tushar.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@Data
public class AccountDto {

    @Schema(
            description = "Account Number of Eazy Bank account", example = "3454433243"
    )
    @NotEmpty
    @Pattern(regexp = "($|[0-9]{10})", message = "AccountNumber must be 10 digit")
    private Long accountNumber;

    @Schema(
            description = "Account type of Eazy Bank account", example = "Savings"
    )
    @NotEmpty(message = "AccountType must not be empty")
    private String accountType;

    @Schema(
            description = "Eazy Bank branch address", example = "123 NewYork"
    )
    @NotEmpty(message = "BranchAddress must not be empty")
    private String branchAddress;

}
