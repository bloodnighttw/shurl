package dev.bntw.shurl.api.request.link;


public record LoginRequest(
    String usernameOrEmail,
    String password
) {
}
