package dev.bntw.shurl.services;


import dev.bntw.shurl.dto.UserRegisterDTO;
import dev.bntw.shurl.exception.EmailExist;
import dev.bntw.shurl.exception.UserNameExist;
import dev.bntw.shurl.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.bntw.shurl.persistence.entity.User;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) throws EmailExist, UserNameExist {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new UserNameExist("username");
        }
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new EmailExist("email");
        }

        var user = new User(userRegisterDTO.getUsername(), userRegisterDTO.getPassword(), userRegisterDTO.getEmail());
        userRepository.save(user);
    }

    public boolean allowLogin(String usernameOrEmail, String password) {

    }

}
