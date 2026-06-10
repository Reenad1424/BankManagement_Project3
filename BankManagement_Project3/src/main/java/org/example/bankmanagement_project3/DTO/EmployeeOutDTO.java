package org.example.bankmanagement_project3.DTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOutDTO {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String position;
    private Double salary;
}
