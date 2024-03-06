package com.tushar.accounts.service.impl;

import com.tushar.accounts.constants.AccountConstants;
import com.tushar.accounts.dto.AccountDto;
import com.tushar.accounts.dto.CustomerDto;
import com.tushar.accounts.entity.Accounts;
import com.tushar.accounts.entity.Customer;
import com.tushar.accounts.exception.CustomerAlreadyExistException;
import com.tushar.accounts.exception.ResourceNotFoundException;
import com.tushar.accounts.mapper.AccountMapper;
import com.tushar.accounts.mapper.CustomerMapper;
import com.tushar.accounts.repository.AccountRepository;
import com.tushar.accounts.repository.CustomerRepository;
import com.tushar.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /*
    @param customerDto - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomerEntity(new Customer(), customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("customer already exist with given mobile number : "
                    +customer.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    /*
    @param customer - Customer object
    @return new account details
     */
    private Accounts createAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());

        //below line will generate the random account number from 100000000L to 999999999L as we are adding them in long
        long accountNumber = 100000000L + new Random().nextInt(900000000);

        newAccounts.setAccountNumber(accountNumber);
        newAccounts.setAccountType(AccountConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountConstants.ADDRESS);
//        newAccounts.setCreatedAt(LocalDateTime.now());
//        newAccounts.setCreatedBy("Anonymous");
        return accountRepository.save(newAccounts);
    }

    /*
    @param mobileNumber - mobile number of the customer
    @return account details based on given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(accounts, new AccountDto()));
        return customerDto;
    }

    /*
    @param customerDto - CustomerDto object
    @return boolean indicating if the update of Account is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(customerDto != null){
            Accounts accounts = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account",
                                        "accountNumber", String.valueOf(accountDto.getAccountNumber())));
            Accounts account = AccountMapper.mapToAccountsEntity(accountDto, accounts);
            accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer  = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );
            Customer customer1 = CustomerMapper.mapToCustomerEntity(customer, customerDto);
            customerRepository.save(customer1);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
