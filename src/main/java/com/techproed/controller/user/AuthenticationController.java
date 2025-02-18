package com.techproed.controller.user;

import com.techproed.payload.request.authentication.LoginRequest;
import com.techproed.payload.response.authentication.AuthenticationResponse;
import com.techproed.service.user.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

}
