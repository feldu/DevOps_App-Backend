package devops.app.controller;


import devops.app.dto.UserSignUpDTO;
import devops.app.entity.Role;
import devops.app.entity.User;
import devops.app.service.RoleService;
import devops.app.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

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

    @SneakyThrows
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        User user = new User(userSignUpDTO.getUsername(), userSignUpDTO.getPassword());
        Set<Role> roles = new HashSet<>();
        for (String roleName : userSignUpDTO.getRoleNames()) {
            Role role = roleService.findByName(roleName);
            roles.add(role);
        }
        user.setRoles(roles);
        log.debug("POST request to register user {}", user);
        log.debug("User role {}", roles);
        boolean isSaved = userService.saveUser(user);
        return isSaved ? new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK) :
                new ResponseEntity<>("Пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);
    }
}
