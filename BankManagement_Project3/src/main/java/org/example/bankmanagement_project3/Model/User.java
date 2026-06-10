package org.example.bankmanagement_project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Length must be between 4 and 10 characters")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be null")
    @Size(min = 6, message = "Length must be at least 6 characters")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    private String email;

    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN", message = "Role must be either CUSTOMER, EMPLOYEE or ADMIN only")
    private String role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Employee employee;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
