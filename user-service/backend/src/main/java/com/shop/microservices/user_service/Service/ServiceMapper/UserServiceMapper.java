package com.shop.microservices.user_service.Service.ServiceMapper;

import com.shop.microservices.user_service.Dto.UserRequestDTO;
import com.shop.microservices.user_service.Dto.UserResponseDTO;
import com.shop.microservices.user_service.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceMapper {
    public User toEntity(UserRequestDTO userRequestDTO){
        if(userRequestDTO ==null){
            return null;
        }
        return
                User.builder()
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .email(userRequestDTO.getEmail())
                .mobileNo(userRequestDTO.getMobileNo())
                .status(userRequestDTO.getStatus())
                .twoFactorEnabled(userRequestDTO.isTwoFactorEnabled())
                .build();
    }

//    public UserResponseDTO ToDto(User user){
//        if(user ==null){
//            return null;
//        }
//
//        return UserResponseDTO.builder()
//    }
}
