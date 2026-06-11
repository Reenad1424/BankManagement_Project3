package org.example.bankmanagement_project3.Service;

import lombok.RequiredArgsConstructor;

import org.example.bankmanagement_project3.Api.ApiException;
import org.example.bankmanagement_project3.DTO.AccountOutDTO;
import org.example.bankmanagement_project3.Model.Account;
import org.example.bankmanagement_project3.Model.User;
import org.example.bankmanagement_project3.Repository.AccountRepository;
import org.example.bankmanagement_project3.Repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;

    private AccountOutDTO convertToOutDTO(Account account) {
        AccountOutDTO dto = new AccountOutDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setActive(account.isActive());
        dto.setCustomerName(account.getCustomer().getUser().getName());
        return dto;
    }

    public List<AccountOutDTO> getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();
        List<AccountOutDTO> dtoList = new ArrayList<>();
        for (int i = 0; i < allAccounts.size(); i++) {
            dtoList.add(convertToOutDTO(allAccounts.get(i)));
        }
        return dtoList;
    }

    public void createAccount(Integer authUserId, Account account) {
        User user = authRepository.findMyUserById(authUserId);
        if (user == null || user.getCustomer() == null) {
            throw new ApiException("Customer profile not found");
        }
        account.setCustomer(user.getCustomer());
        account.setActive(false);
        accountRepository.save(account);
    }

    public void activateAccount(Integer authUserId, Integer accountId) {
        User user = authRepository.findMyUserById(authUserId);
        if (user == null || (!user.getRole().equals("EMPLOYEE") && !user.getRole().equals("ADMIN"))) {
            throw new ApiException("sorry , you dont have the authority to activate accounts");
        }

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        account.setActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(Integer authUserId, Integer accountId) {
        User user = authRepository.findMyUserById(authUserId);
        if (user == null || (!user.getRole().equals("EMPLOYEE") && !user.getRole().equals("ADMIN"))) {
            throw new ApiException("sorry , you dont have the authority to block accounts");
        }

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        account.setActive(false);
        accountRepository.save(account);
    }

    public List<AccountOutDTO> getMyAccounts(Integer authUserId) {
        User user = authRepository.findMyUserById(authUserId);
        if (user == null || user.getCustomer() == null) {
            throw new ApiException("Customer not found");
        }

        List<Account> allAccounts = accountRepository.findAll();
        List<AccountOutDTO> myDtoAccounts = new ArrayList<>();

        for (int i = 0; i < allAccounts.size(); i++) {
            Account acc = allAccounts.get(i);
            if (acc.getCustomer().getId().equals(user.getCustomer().getId())) {
                myDtoAccounts.add(convertToOutDTO(acc));
            }
        }
        return myDtoAccounts;
    }

    public void deposit(Integer authUserId, Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        if (!account.getCustomer().getUser().getId().equals(authUserId)) {
            throw new ApiException("sorry , you dont have the authority to deposit into this account");
        }
        if (!account.isActive()) throw new ApiException("Account is not active");

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(Integer authUserId, Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        if (!account.getCustomer().getUser().getId().equals(authUserId)) {
            throw new ApiException("sorry , you dont have the authority to withdraw from this account");
        }
        if (!account.isActive()) throw new ApiException("Account is not active");
        if (account.getBalance() < amount) throw new ApiException("Insufficient balance");

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(Integer authUserId, Integer fromAccountId, String toAccountNumber, Double amount) {
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountByAccountNumber(toAccountNumber);

        if (fromAccount == null) throw new ApiException("Source account not found");
        if (toAccount == null) throw new ApiException("Destination account not found");

        if (!fromAccount.getCustomer().getUser().getId().equals(authUserId)) {
            throw new ApiException("sorry , you dont have the authority to transfer from this account");
        }
        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new ApiException("Both accounts must be active to complete the transfer");
        }
        if (fromAccount.getBalance() < amount) throw new ApiException("Insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
