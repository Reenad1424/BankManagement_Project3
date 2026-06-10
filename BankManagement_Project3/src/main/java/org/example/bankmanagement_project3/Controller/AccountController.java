package org.example.bankmanagement_project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.bankmanagement_project3.Api.ApiResponse;
import org.example.bankmanagement_project3.DTO.AccountOutDTO;
import org.example.bankmanagement_project3.Model.Account;
import org.example.bankmanagement_project3.Model.User;
import org.example.bankmanagement_project3.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-all")
    public ResponseEntity<List<AccountOutDTO>> getAllAccounts() {
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(201).body(new ApiResponse("Account created successfully, awaiting activation"));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponse> activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        accountService.activateAccount(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<ApiResponse> blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        accountService.blockAccount(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }

    @GetMapping("/view-my-account/{id}")
    public ResponseEntity<AccountOutDTO> getMyAccountDetails(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(accountService.getMyAccountDetails(user.getId(), id));
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<AccountOutDTO>> getMyAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getMyAccounts(user.getId()));
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<ApiResponse> deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit completed successfully"));
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<ApiResponse> withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdraw completed successfully"));
    }

    @PutMapping("/transfer/{fromAccountId}/{toAccountNumber}/{amount}")
    public ResponseEntity<ApiResponse> transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId, @PathVariable String toAccountNumber, @PathVariable Double amount) {
        accountService.transfer(user.getId(), fromAccountId, toAccountNumber, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Funds transferred successfully"));
    }
}
