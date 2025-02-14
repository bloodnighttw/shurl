package dev.bntw.shurl.persistence.repository;

import dev.bntw.shurl.persistence.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

     boolean existsByAlias(String alias);

}
