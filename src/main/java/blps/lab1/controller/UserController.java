package blps.lab1.controller;

import blps.lab1.dto.UserUpdateDTO;
import blps.lab1.entity.User;
import blps.lab1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //todo: fix it
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO userDTO,
                                             @PathVariable Long id) {
        User user = new User(userDTO);
        user.setId(id);
        log.debug("Try to update user: {}", user);
        userService.updateUser(id, user);
        return new ResponseEntity<>("Пользователь изменён", HttpStatus.OK);
    }
}
