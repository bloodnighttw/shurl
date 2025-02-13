package dev.bntw.shurl.exception;

public class EmailExist extends Throwable {
    public EmailExist(String email) {
        super("Email with " + email + " already exists");
    }
}
