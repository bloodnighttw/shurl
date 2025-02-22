package dev.bntw.shurl.api.request.auth;


public record LoginRequest(
    String usernameOrEmail,
    String password
) {
}
