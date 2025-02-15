package dev.bntw.shurl.exception.user;

public class EmailOrUsernameAlreadyExist extends Throwable {
    public EmailOrUsernameAlreadyExist(String email,String username) {
        super("Email with " + email + " exists or Username with " + username + " exists");
    }
}
