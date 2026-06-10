package org.example.bankmanagement_project3.DTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {

    @NotBlank(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Length must be between 4 and 10 characters")
    private String username;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 6, message = "Length must be at least 6 characters")
    private String password;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Must start with 05")
    private String phoneNumber;
}
