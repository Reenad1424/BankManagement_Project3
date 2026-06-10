package org.example.bankmanagement_project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Must be a non-negative decimal number")
    private Double salary;

    @OneToOne
    @JsonIgnore
    private User user;
}
