package com.tushar.accounts.controller;

import com.tushar.accounts.constants.AccountConstants;
import com.tushar.accounts.dto.CustomerDto;
import com.tushar.accounts.dto.ErrorResponseDto;
import com.tushar.accounts.dto.ResponseDto;
import com.tushar.accounts.entity.Customer;
import com.tushar.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Crus REST API for ACCOUNTS in EazyBank",
        description = "CRUD REST API in EAZYBANK to CREATE, READ, UPDATE, DELETE and FETCH ACCOUNTS details"
) //tag annotation will provide all the information In this controller class
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE}) //PRODUCES will declare that the return type must be the JSON
@Validated
public class AccountsController {

    private IAccountService accountService;

    public AccountsController(IAccountService accountService){
        this.accountService = accountService;
    }



    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content( //here we are declaring that the api will throw ErrorResponseDto.class
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "($|[0-9]{10})",
                                            message = "mobileNumber must have the 10 digits") String mobileNumber){
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content( //here we are declaring that the api will throw ErrorResponseDto.class
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_201));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE)
            );
        }
    }


    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content( //here we are declaring that the api will throw ErrorResponseDto.class
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp = "($|[0-9]{10})",
                                        message = "mobileNumber must be the 10 digits") String mobileNumber){
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200)
            );
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE)
            );
        }
    }

}
