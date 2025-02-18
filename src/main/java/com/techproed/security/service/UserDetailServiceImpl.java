package com.techproed.security.service;

import com.techproed.entity.concretes.user.User;
import com.techproed.service.helper.MethodHelper;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class  UserDetailServiceImpl implements UserDetailsService {

    private final MethodHelper methodHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = methodHelper.loadByUsername(username);
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getUserRole().getRoleType().getName());
    }
}
