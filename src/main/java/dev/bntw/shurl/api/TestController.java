package dev.bntw.shurl.api;

import dev.bntw.shurl.dto.UserRegisterDTO;
import dev.bntw.shurl.exception.EmailExist;
import dev.bntw.shurl.exception.UserNameExist;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.persistence.repository.UserRepository;
import dev.bntw.shurl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test2() {
        return "Test 2";
    }

    @GetMapping("/{id}")
    public String idTest(@PathVariable int id) {
        try {
            userService.registerUser(new UserRegisterDTO("test" + id, "test" + id, "test" + id));
        } catch (EmailExist e) {
            return "Email " + id + " exist";
        } catch (UserNameExist e) {
            return "Username " + id + " exist";
        }

        return "User " + id + " created";
    }

}
