package com.shop.microservices.user_service.Service.Serviceinterface;

import com.shop.microservices.user_service.Dto.UserRequestDTO;
import com.shop.microservices.user_service.Dto.UserResponseDTO;

import java.util.List;

public interface IUserService {
    //add new user
    UserResponseDTO AddNewUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> GetAllUser();
}
