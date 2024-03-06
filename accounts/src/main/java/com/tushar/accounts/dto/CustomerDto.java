package com.tushar.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Tushar Fadol"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 4, max = 40, message = "Name must be between 4 and 40 characters")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tushar@fadol.com"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email is not valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "($|[0-9]{10})", message = "mobile number must be 10 digit")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountDto accountDto;

}
