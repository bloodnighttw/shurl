package dev.bntw.shurl.services;

import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.persistence.repository.UserRepository;
import dev.bntw.shurl.utils.MemberDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username +" not found");
        }

        return new MemberDetail(user);
    }

    public User login(String usernameOrEmail, String password) {
        var user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        if(user == null) {
            throw new BadCredentialsException("Username or email not found");
        }

        if(!passwordEncoder.matches(password,user.getHashPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return user;
    }
}
