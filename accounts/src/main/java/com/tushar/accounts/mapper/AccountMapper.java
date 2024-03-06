package com.tushar.accounts.mapper;

import com.tushar.accounts.dto.AccountDto;
import com.tushar.accounts.entity.Accounts;

public class AccountMapper {

    public static Accounts mapToAccountsEntity(AccountDto accountDTO, Accounts accounts) {
        accounts.setAccountNumber(accountDTO.getAccountNumber());
        accounts.setAccountType(accountDTO.getAccountType());
        accounts.setBranchAddress(accountDTO.getBranchAddress());
        return accounts;
    }

    public static AccountDto mapToAccountDto(Accounts accounts, AccountDto accountDTO) {
        accountDTO.setAccountNumber(accounts.getAccountNumber());
        accountDTO.setAccountType(accounts.getAccountType());
        accountDTO.setBranchAddress(accounts.getBranchAddress());
        return accountDTO;
    }

}
