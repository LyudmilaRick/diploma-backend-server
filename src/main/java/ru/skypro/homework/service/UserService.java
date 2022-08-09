package ru.skypro.homework.service;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.ResponseWrapperUserDto;

public interface UserService {

    @Deprecated
    CreateUser addUser(CreateUser user);

    ResponseWrapperUserDto getUsers();

    UserDto getUser(Integer id);

    UserDto getUser(String name);

    UserDto updateUser(String username, UserDto user);

    NewPasswordDto setPassword(String userName, String oldPassword, String newPassword);
}
