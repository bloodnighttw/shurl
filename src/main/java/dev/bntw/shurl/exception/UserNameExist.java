package dev.bntw.shurl.exception;

public class UserNameExist extends RuntimeException {
    public UserNameExist(String keyName) {
        super("Username with " + keyName + " already exists");
    }
}
