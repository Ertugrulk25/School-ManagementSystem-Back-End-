package com.techproed.service.user;

import com.techproed.payload.request.authentication.LoginRequest;
import com.techproed.payload.response.authentication.AuthenticationResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.security.jwt.JwtUtils;
import com.techproed.security.service.UserDetailsImpl;
import com.techproed.service.helper.MethodHelper;
import io.jsonwebtoken.Jwts;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;



    public AuthenticationResponse authenticate(@Valid LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //injection of security in service
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        //security authentication bu satirda yapiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        return AuthenticationResponse.builder()
                .token(token)
                .role(userRole)
                .username(userDetails.getUsername())
                .build();
    }
}
