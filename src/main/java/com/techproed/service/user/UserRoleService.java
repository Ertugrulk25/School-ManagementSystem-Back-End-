package com.techproed.service.user;


import com.techproed.entity.concretes.user.UserRole;
import com.techproed.entity.enums.RoleType;
import com.techproed.exception.ResourceNotFoundException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByUserRoleType(roleType)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRoles(){

        return userRoleRepository.findAll();
    }
}
