package org.example.bankmanagement_project3.Service;

import lombok.RequiredArgsConstructor;

import org.example.bankmanagement_project3.DTO.*;
import org.example.bankmanagement_project3.Model.Account;
import org.example.bankmanagement_project3.Model.Customer;
import org.example.bankmanagement_project3.Model.Employee;
import org.example.bankmanagement_project3.Model.User;
import org.example.bankmanagement_project3.Repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void registerCustomer(CustomerRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("CUSTOMER");

        String hashPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hashPassword);

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);

        user.setCustomer(customer);
        authRepository.save(user);
    }

    public void registerEmployee(EmployeeRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("EMPLOYEE");

        String hashPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hashPassword);

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);

        user.setEmployee(employee);
        authRepository.save(user);
    }

    public List<Object> getAllUsers() {
        List<User> users = authRepository.findAll();
        List<Object> dtos = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);

            if ("CUSTOMER".equals(u.getRole()) && u.getCustomer() != null) {
                List<AccountOutDTO> accDtos = new ArrayList<>();

                if (u.getCustomer().getAccounts() != null) {
                    for (Account acc : u.getCustomer().getAccounts()) {
                        AccountOutDTO accDto = new AccountOutDTO(
                                acc.getId(),
                                acc.getAccountNumber(),
                                acc.getBalance(),
                                acc.isActive(),
                                u.getName()
                        );
                        accDtos.add(accDto);
                    }
                }

                CustomerOutDTO customerDto = new CustomerOutDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        u.getCustomer().getPhoneNumber(),
                        accDtos
                );
                dtos.add(customerDto);

            } else if ("EMPLOYEE".equals(u.getRole()) && u.getEmployee() != null) {
                EmployeeOutDTO employeeDto = new EmployeeOutDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        u.getEmployee().getPosition(),
                        u.getEmployee().getSalary()
                );
                dtos.add(employeeDto);
            } else {
                dtos.add(u);
            }
        }
        return dtos;
    }
}
