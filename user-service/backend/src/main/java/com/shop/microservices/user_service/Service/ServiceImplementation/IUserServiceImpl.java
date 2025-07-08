package com.shop.microservices.user_service.Service.ServiceImplementation;

import com.shop.microservices.user_service.Dto.UserRequestDTO;
import com.shop.microservices.user_service.Dto.UserResponseDTO;
import com.shop.microservices.user_service.Model.User;
import com.shop.microservices.user_service.Repository.IUserRepository;
import com.shop.microservices.user_service.Service.ServiceMapper.UserServiceMapper;
import com.shop.microservices.user_service.Service.Serviceinterface.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional

public class IUserServiceImpl implements IUserService{

    @Autowired
    private final UserServiceMapper userServiceMapper;
    private final IUserRepository iUserRepository;


    public IUserServiceImpl(UserServiceMapper userServiceMapper, IUserRepository iUserRepository){
        this.userServiceMapper=userServiceMapper;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserResponseDTO AddNewUser(UserRequestDTO userRequestDTO){
        User entity=userServiceMapper.toEntity(userRequestDTO);
        User saved =iUserRepository.save(entity);
        return userServiceMapper.ToDto(saved);
    }

}