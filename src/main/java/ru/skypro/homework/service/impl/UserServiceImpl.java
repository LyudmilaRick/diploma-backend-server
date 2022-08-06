package ru.skypro.homework.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreateUser addUser(CreateUser user) {
        UserEntity entity = new UserEntity(null, user.getEmail(), user.getPassword(), Role.USER, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
        UserEntity result = repository.saveAndFlush(entity);
        return convertDtoCreateUserToUserEntity(result);
    }

    @Override
    public User getUser(Integer id) {
        Optional<UserEntity> result = repository.findById(id);
        if (result.isPresent()) {
            return new User(result.get());
        }

        throw new NullPointerException("User '" + id + "' not found.");
    }

    @Override
    public ResponseWrapperUser getUsers() {
        return new ResponseWrapperUser(repository.findAll());
    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = new UserEntity(user.getId(), user.getEmail(), null, Role.USER, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
        UserEntity result = repository.save(entity);
        return convertDtoUserToUserEntity(result);
    }

    @Override
    public NewPassword setPassword(String userName, String oldPassword, String newPassword) {
        //TODO:
        //.ifPresent(resultPassword::setCurrentPassword);
        throw new UnsupportedOperationException("Not 'UserServiceImpl.setPassword()' supported yet.");
    }

    private CreateUser convertDtoCreateUserToUserEntity(UserEntity user) {
        CreateUser userDto = new CreateUser();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

    private User convertDtoUserToUserEntity(UserEntity user) {
        return new User(user);
    }

}
