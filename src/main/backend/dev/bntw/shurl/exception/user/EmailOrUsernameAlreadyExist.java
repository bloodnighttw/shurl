package dev.bntw.shurl.exception.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailOrUsernameAlreadyExist extends RuntimeException {
    public EmailOrUsernameAlreadyExist(String email,String username) {
        super("Email with " + email + " exists or Username with " + username + " exists");
    }
}
