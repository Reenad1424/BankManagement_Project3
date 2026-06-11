package org.example.bankmanagement_project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankmanagement_project3.Api.ApiResponse;
import org.example.bankmanagement_project3.DTO.CustomerRegisterInDTO;
import org.example.bankmanagement_project3.DTO.EmployeeRegisterInDTO;
import org.example.bankmanagement_project3.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-customer")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody @Valid CustomerRegisterInDTO dto){
        authService.registerCustomer(dto);
        return ResponseEntity.status(201).body(new ApiResponse("Customer registered successfully"));
    }

    @PostMapping("/register-employee")
    public ResponseEntity<ApiResponse> registerEmployee(@RequestBody @Valid EmployeeRegisterInDTO dto){
        authService.registerEmployee(dto);
        return ResponseEntity.status(201).body(new ApiResponse("Employee registered successfully"));
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<Object>> getAllUsers() {
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }
}
