package dev.bntw.shurl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@AllArgsConstructor
@Getter
public final class UserRegisterDTO{
    private final String username;
    private final String password;
    private final String email;

}
