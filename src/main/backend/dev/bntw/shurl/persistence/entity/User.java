package dev.bntw.shurl.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "USERS")
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, name = "password")
    private String hashPassword;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Link> shortUrls;

    public User(String username, String hashPassword, String email) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // TODO: ADD PERMISSIONS

}
