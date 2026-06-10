package org.example.bankmanagement_project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.bankmanagement_project3.Api.ApiException;
import org.example.bankmanagement_project3.Model.User;
import org.example.bankmanagement_project3.Repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // اسمها ثابت حتى لو بشيك على ايميل او جوال
        User user = authRepository.findUserByUsername(username);

        if(user == null){
            throw new ApiException("wrong username or password "); // ثابته الكلمة عشان محد يدري وش الصح لو صار اختراق
        }
        return user;
    }
}
