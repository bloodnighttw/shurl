package dev.bntw.shurl.persistence.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "LINKS")
public class Link {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String alias;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(nullable = false)
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User auth;

    public Link(String alias, String url, @Nullable User auth) {
        this.alias = alias;
        this.url = url;
        this.auth = auth;
    }

    @PrePersist
    public void onCreated(){
        this.created = new Date();
    }
}
