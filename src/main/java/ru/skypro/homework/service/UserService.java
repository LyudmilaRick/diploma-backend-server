package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;

public interface UserService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
