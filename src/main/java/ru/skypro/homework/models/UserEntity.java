package ru.skypro.homework.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import ru.skypro.homework.dto.Role;

/**
 * Класс сущности пользователя сайта.
 */
@Entity
@Table(name = "user_data")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", unique = true)
    private Integer idUser;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rolename")
    private Role rolename;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String phone;

    public UserEntity(Integer id, String username, String password, Role rolename, String firstName, String lastName, String email, String phone) {
        this.idUser = id;
        this.username = username;
        this.password = password;
        this.rolename = rolename;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public UserEntity() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRolename() {
        return rolename;
    }

    public void setRolename(Role rolename) {
        this.rolename = rolename;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity other = (UserEntity) obj;
        return Objects.equals(this.idUser, other.idUser);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + idUser + ", username=" + username + ", password=" + password + ", rolename=" + rolename + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + '}';
    }

}
