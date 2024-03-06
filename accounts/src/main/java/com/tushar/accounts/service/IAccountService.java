package com.tushar.accounts.service;

import com.tushar.accounts.dto.CustomerDto;

public interface IAccountService {

    /*
    @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /*
    @param mobileNumber - Mobile Number
    @return account details based on given mobileNumber
     */
    CustomerDto fetchAccount(String mobileNumber);

    /*
    @param customerDto - CustomerDto Object
    @return boolean indicating if the update of Accounts details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /*
    @param customerDto CustomerDto object
    @return boolean indicating if the deletion of Accounts is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
