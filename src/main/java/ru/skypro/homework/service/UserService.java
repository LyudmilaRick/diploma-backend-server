package ru.skypro.homework.service;

import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;

public interface UserService {
    CreateUser addUser(CreateUser user);
    User getUser(Integer id);
    ResponseWrapperUser getUsers();
    User updateUser(User user);
    NewPassword setPassword(String userName, String oldPassword, String newPassword);
}
