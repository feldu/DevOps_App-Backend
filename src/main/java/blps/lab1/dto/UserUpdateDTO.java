package blps.lab1.dto;

import blps.lab1.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserUpdateDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String city;
    private Date startDrivingYear;
    private Date birthDate;
    private String job;
    private String hobby;
    private Set<Role> roles = new HashSet<>();
}
