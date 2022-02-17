package blps.lab1.controller;

import blps.lab1.entity.User;
import blps.lab1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return new ResponseEntity<>("Пользователь изменён", HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Пользователь не найден в базе", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }
}
