package dev.bntw.shurl.persistence.entity;


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

    public Link(String alias, String url) {
        this.alias = alias;
        this.url = url;
    }

    @PrePersist
    public void onCreated(){
        this.created = new Date();
    }
}
