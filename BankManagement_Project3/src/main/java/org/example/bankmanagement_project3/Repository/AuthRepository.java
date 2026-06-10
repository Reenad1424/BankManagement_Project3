package org.example.bankmanagement_project3.Repository;

import org.example.bankmanagement_project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    User findMyUserById(Integer id);
    User findUserByUsername(String username);
}
