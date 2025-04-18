package com.techproed.controller.user;

import com.techproed.payload.request.user.UserRequest;
import com.techproed.payload.request.user.UserRequestWithoutPassword;
import com.techproed.payload.response.abstracts.BaseUserResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.service.user.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable String userRole
    ){
        return ResponseEntity.ok(userService.saveUser(userRequest,userRole));
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/getUserByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>>getUserByPage(
            @PathVariable String userRole,
            @RequestParam (value = "page",defaultValue = "0") int page,
            @RequestParam (value = "size",defaultValue = "10") int size,
            @RequestParam (value = "sort",defaultValue = "name") String sort,
            @RequestParam (value = "type",defaultValue = "desc") String type){
        Page<UserResponse>userResponses= userService.getUserByPage(page,size,sort,type,userRole);
        return ResponseEntity.ok(userResponses);
    }

    @PreAuthorize("hasAnyAuthority('Admin','VideDean','Dean')")
    @GetMapping("/getUserById/{userId}")
    public ResponseMessage<BaseUserResponse>getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String>deleteUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/update/{userId}")
    public ResponseMessage<UserResponse>updateUserById(
            @RequestBody @Valid UserRequest userRequest
            ,@PathVariable Long userId) {
        return userService.updateUserById(userRequest,userId);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PatchMapping("/updateLoggedInUser")
    public ResponseEntity<String>updateLoggedInUser(
            @RequestBody @Valid UserRequestWithoutPassword userRequestWithoutPassword,
            HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.updateLoggedInUser(userRequestWithoutPassword,httpServletRequest));
    }






}