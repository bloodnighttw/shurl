package dev.bntw.shurl.services;


import dev.bntw.shurl.dto.UserRegisterDTO;
import dev.bntw.shurl.exception.user.EmailOrUsernameAlreadyExist;
import dev.bntw.shurl.persistence.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public void registerUser(UserRegisterDTO userRegisterDTO) throws EmailOrUsernameAlreadyExist {
        var hashPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        try {
            var user = new User(userRegisterDTO.getUsername(), hashPassword, userRegisterDTO.getEmail());
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new EmailOrUsernameAlreadyExist(userRegisterDTO.getEmail(),userRegisterDTO.getPassword());
        }
    }

}
