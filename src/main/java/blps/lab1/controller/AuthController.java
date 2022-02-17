package blps.lab1.controller;


import blps.lab1.entity.Role;
import blps.lab1.entity.User;
import blps.lab1.service.RoleService;
import blps.lab1.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO dto) {
        try {
            Role role = roleService.findByName(dto.getRole());
            if (role == null)
                return new ResponseEntity<>("Указанная роль не существует", HttpStatus.BAD_REQUEST);
            User user = new User(dto.getUsername(), dto.getPassword());
            user.setRoles(Collections.singleton(role));
            log.debug("POST request to register user {}", user);
            log.debug("User role {}", role);
            boolean isSaved = userService.saveUser(user);
            return isSaved ? new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK) :
                    new ResponseEntity<>("Пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error {}", e.getMessage());
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    private static class UserDTO {
        private String username;
        private String password;
        private String role;
    }
}
