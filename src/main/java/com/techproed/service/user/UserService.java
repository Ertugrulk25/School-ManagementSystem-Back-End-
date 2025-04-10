package com.techproed.service.user;

import com.techproed.entity.concretes.user.User;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.request.user.UserRequest;
import com.techproed.payload.request.user.UserRequestWithoutPassword;
import com.techproed.payload.response.abstracts.BaseUserResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.helper.PageableHelper;
import com.techproed.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<UserResponse> saveUser(@Valid UserRequest userRequest, String userRole) {
    //validate unique prop.
        uniquePropertyValidator.checkDuplication(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail()
        );
        //DTO-Entity mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest,userRole);
        //save operation
        User saveUser = userRepository.save(userToSave);
//        //entity ->DTO mapping
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(saveUser))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<BaseUserResponse> findUserById(Long userId) {
        //validate if user exist in DB
        User user = methodHelper.isUserExist(userId);
        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                //map entity to DTO
                .returnBody(userMapper.mapUserToUserResponse(user))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public String deleteUserById(Long userId) {

   methodHelper.isUserExist(userId);
   userRepository.deleteById(userId);
   return SuccessMessages.USER_DELETE;

    }

    public Page<UserResponse> getUserByPage(int page, int size, String sort, String type, String userRole
    ) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);

        return userRepository.findUserByUserRoleQuery(userRole,pageable)
                .map(userMapper::mapUserToUserResponse);
    }

    public ResponseMessage<UserResponse> updateUserById( UserRequest userRequest, Long userId) {

   //validate if user exist
       User userFromDb = methodHelper.isUserExist(userId);
        //building in users can not be updated
        methodHelper.checkBuildIn(userFromDb);
        //unique property validators
uniquePropertyValidator.checkUniqueProperty(userFromDb, userRequest);
//mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest,userFromDb.getUserRole().getRoleName());
        userToSave.setId(userId);
        User saveUser = userRepository.save(userToSave);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToUserResponse(saveUser))
                .build();




    }

    public String updateLoggedInUser(@Valid UserRequestWithoutPassword userRequestWithoutPassword,
                                     HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User user = userRepository.findByUsername(username);
        methodHelper.checkBuildIn(user);
        uniquePropertyValidator.checkUniqueProperty(user,userRequestWithoutPassword);
        user.setName(userRequestWithoutPassword.getName());
        user.setSurname(userRequestWithoutPassword.getSurname());
        user.setSsn(userRequestWithoutPassword.getSsn());
        user.setUsername(userRequestWithoutPassword.getUsername());
        user.setBirthday(userRequestWithoutPassword.getBirthDay());
        user.setBirthplace(userRequestWithoutPassword.getBirthPlace());
        user.setEmail(userRequestWithoutPassword.getEmail());
        user.setPhoneNumber(userRequestWithoutPassword.getPhoneNumber());
        user.setGender(userRequestWithoutPassword.getGender());
        userRepository.save(user);
        return SuccessMessages.USER_UPDATE;



    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
