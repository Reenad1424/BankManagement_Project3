package org.example.bankmanagement_project3.Repository;

import org.example.bankmanagement_project3.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountById(Integer id);
    Account findAccountByAccountNumber(String accountNumber);
}
