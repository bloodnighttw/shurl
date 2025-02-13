package dev.bntw.shurl.services;


import dev.bntw.shurl.dto.UserRegisterDTO;
import dev.bntw.shurl.exception.EmailExist;
import dev.bntw.shurl.exception.UserNameExist;
import dev.bntw.shurl.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.bntw.shurl.persistence.entity.User;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) throws EmailExist, UserNameExist {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new UserNameExist("username");
        }
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new EmailExist("email");
        }

        var hashPassword = passwordEncoder.encode(userRegisterDTO.getPassword());

        var user = new User(userRegisterDTO.getUsername(), hashPassword, userRegisterDTO.getEmail());
        userRepository.save(user);
    }

}
