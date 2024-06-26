package devops.app.controller;

import devops.app.dto.UserInfoDTO;
import devops.app.entity.User;
import devops.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/authenticated")
    public ResponseEntity<UserInfoDTO> getUserInfo(Authentication authentication) {
        User user = userService.loadUserByUsername(authentication.getName());
        UserInfoDTO userDto = new UserInfoDTO(user.getUsername(), user.getName());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
