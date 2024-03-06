package com.tushar.accounts.repository;

import com.tushar.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /*
    - here we are taking advantage of Spring Data Jpa repository as JPA will create the SQL query
    - we just have to write the method name and it will create the query for us.
    after findBy..... there must be the column name of the table we want to query
    - column name must be same as Entity class variable name
    - we make method as Optional because we are not sure if the customer/entity exists or not.
    - additionally we can use @Query annotation so we will write an sql query
    */
    Optional<Customer> findByMobileNumber(String MobileNumber);

}
