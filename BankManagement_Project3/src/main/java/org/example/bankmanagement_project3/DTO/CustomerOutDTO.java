package org.example.bankmanagement_project3.DTO;

import lombok.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOutDTO {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;
    private List<AccountOutDTO> accounts;
}
