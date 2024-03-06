package com.tushar.accounts.repository;

import com.tushar.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(long customerId);

    /*
    @Modifying - This annotation is used to indicate that the method is modifying the data in the database.
    @Transactional - This annotation is used to indicate that the method is transactional. So if ther is any error will
                occure then the RollBack will take place.
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
