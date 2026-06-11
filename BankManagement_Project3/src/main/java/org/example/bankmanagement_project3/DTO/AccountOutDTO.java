package org.example.bankmanagement_project3.DTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountOutDTO {
    private Integer id;
    private String accountNumber;
    private Double balance;
    private boolean isActive;
    private String customerName;
}
