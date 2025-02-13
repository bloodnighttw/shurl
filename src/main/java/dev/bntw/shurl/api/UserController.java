package dev.bntw.shurl.api;

import dev.bntw.shurl.dto.UserRegisterDTO;
import dev.bntw.shurl.exception.user.EmailOrUsernameAlreadyExist;
import dev.bntw.shurl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("${shuri.api-prefix}/register")
    public String register(@RequestBody UserRegisterDTO register) throws EmailOrUsernameAlreadyExist {
        userService.registerUser(register);
        return "User registered";
    }

}
