package dev.bntw.shurl.services;

import dev.bntw.shurl.persistence.entity.Link;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.persistence.repository.LinkRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;

    @Value("${shurl.alias.length:6}")
    private final int ALIAS_LENGTH = 6;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public String createLink(String url, @Nullable User user){
        do{
            var alias = generateAlias();

            if(linkRepository.existsByAlias(alias)) {
                log.warn("Alias {} already exists, regenerating......", alias);
                continue;
            }

            linkRepository.save(new Link(alias, url, user));
            return alias;
        }while (true);
    }

    public Link getLink(String alias){
        return linkRepository.findByAlias(alias);
    }

    private String generateAlias(){
        var sb = new StringBuilder(ALIAS_LENGTH);
        for(int i = 0; i < ALIAS_LENGTH; i++){
            sb.append((char) (Math.random() * 26 + 'a'));
        }
        return sb.toString();
    }

}
