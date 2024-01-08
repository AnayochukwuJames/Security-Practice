package com.example.securitypractice.service.serviceImp;

import com.example.securitypractice.repository.UserRepository;
import com.example.securitypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByEmail(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }
};
    }
}
