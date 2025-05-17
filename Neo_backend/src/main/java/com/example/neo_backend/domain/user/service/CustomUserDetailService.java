//package com.example.neo_backend.domain.user.service;
//
//import com.example.neo_backend.domain.user.repository.UserRepository;
//import com.example.neo_backend.domain.user.security.CustomUserDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        com.example.neo_backend.domain.user.entity.User user = userRepository.findByEmail(email);
//
//        if (user != null) {
//            return new CustomUserDetails(user);
//        }
//
//        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
//    }
//
//
//}