package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository users;

    public AuthServiceImpl(UserDetailsManager manager, UserRepository users) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
        this.users = users;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReqDto body, Role role) {
        if (manager.userExists(body.getUsername())) {
            return false;
        }

        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(body.getPassword())
                        .username(body.getUsername())
                        .roles(role.name())
                        .build()
        );
        
        // Используется API метод PATCH /users/me для обновления реквизитов пользователь.
        // А здесь добавляются только AUTH параметры в собственную таблицу (т.е. следующий код правильный).
        UserEntity entity = new UserEntity(null, body.getUsername(), body.getFirstName(), body.getLastName(), body.getUsername(), body.getPhone());
        users.saveAndFlush(entity);

        return true;
    }
}
