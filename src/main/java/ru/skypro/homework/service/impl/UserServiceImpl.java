package ru.skypro.homework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exception.WebNotFoundException;
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
            return convertDtoUserToUserEntity(result.get());
            //return new User(result.get());
        }

        throw new WebNotFoundException("User '" + id + "' not found.");
    }

    @Override
    public ResponseWrapperUser getUsers() {
        List<UserEntity> userEntities = repository.findAll();
        ResponseWrapperUser wrapperUser = new ResponseWrapperUser();
        List<User> users = new ArrayList<>(userEntities.size());
        for (UserEntity item : userEntities) {
             users.add(convertDtoUserToUserEntity(item));
        }
        wrapperUser.setCount(userEntities.size());
        wrapperUser.setResults(users);
        return wrapperUser;
    }

    @Override
    public User updateUser(User user) {
        repository.findById(user.getId())
                  .orElseThrow(() -> new WebNotFoundException("User '" + user.getId() + "' not found."));

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
        User userDto = new User();
        userDto.setId(user.getIdUser());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

}
