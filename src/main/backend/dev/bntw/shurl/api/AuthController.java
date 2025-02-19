package dev.bntw.shurl.api;

import dev.bntw.shurl.api.request.link.LoginRequest;
import dev.bntw.shurl.api.response.LoginResponse;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.services.AuthService;
import dev.bntw.shurl.services.JwtService;
import dev.bntw.shurl.utils.JwtAuth.JwtAuth;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${shuri.api-prefix}/auth")
public class AuthController {

    public final AuthService authService;
    public final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }


    @PostMapping("/")
    @Transactional
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        var user = authService.login(loginRequest.usernameOrEmail(), loginRequest.password());
        var token = jwtService.createToken(user);
        return new LoginResponse(token);
    }

    @PostMapping("/refresh")
    @JwtAuth
    public LoginResponse refresh(User user) {

        var token = jwtService.createToken(user);
        return new LoginResponse(token);
    }

}
