package ru.skypro.homework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.ResponseWrapperUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exceptions.WebNotFoundException;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.repository.UserRepository;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository users;

    public UserServiceImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public CreateUser addUser(CreateUser user) {
        UserEntity entity = new UserEntity(null, user.getEmail(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
        UserEntity result = users.saveAndFlush(entity);
        return convertDtoCreateUserToUserEntity(result);
    }

    @Override
    public ResponseWrapperUserDto getUsers() {
        List<UserEntity> userEntities = users.findAll();
        ResponseWrapperUserDto wrapperUser = new ResponseWrapperUserDto();
        List<UserDto> usersList = new ArrayList<>(userEntities.size());
        for (UserEntity item : userEntities) {
            usersList.add(convertDtoUserToUserEntity(item));
        }
        wrapperUser.setCount(userEntities.size());
        wrapperUser.setResults(usersList);
        return wrapperUser;
    }

    @Override
    public UserDto getUser(Integer id) {
        Optional<UserEntity> result = users.findById(id);
        if (result.isPresent()) {
            return convertDtoUserToUserEntity(result.get());
        }

        throw new WebNotFoundException("User '" + id + "' not found.");
    }

    @Override
    public UserDto getUser(String name) {
        UserEntity result = users.getByUsername(name);
        if (result != null) {
            return convertDtoUserToUserEntity(result);
        }

        throw new WebNotFoundException("User '" + name + "' not found.");
    }

    @Override
    public UserDto updateUser(String username, UserDto user) {
        UserEntity entity = users.getByUsername(username); // т.к. это авторизованный пользователь, то не надо проверять на null
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPhone(user.getPhone());

        UserEntity result = users.saveAndFlush(entity);
        return convertDtoUserToUserEntity(result);
    }

    @Override
    public NewPasswordDto setPassword(String userName, String oldPassword, String newPassword) {
        UserEntity entity = users.getByUsername(userName);

        //TODO: Надо продумать как изменить пароль в БД. Смотрим AuthServiceImpl.register() - manager.updateUser(user);
        throw new UnsupportedOperationException("TODO: UserServiceImpl.setPassword()");
    }


    private static CreateUser convertDtoCreateUserToUserEntity(UserEntity user) {
        CreateUser userDto = new CreateUser();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
//      userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

    private static UserDto convertDtoUserToUserEntity(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getIdUser());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

}
