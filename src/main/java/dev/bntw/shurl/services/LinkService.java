package dev.bntw.shurl.services;

import dev.bntw.shurl.persistence.entity.Link;
import dev.bntw.shurl.persistence.repository.LinkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    @Value("${shurl.alias.length:6}")
    private final int ALIAS_LENGTH = 6;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void createLink(String url){

        while(true){
            var alias = generateAlias();

            if(linkRepository.existsByAlias(alias))
                continue;

            try {
                linkRepository.save(new Link(alias, url));
                return;
            } catch (DataIntegrityViolationException e){
                // alias already exists
            }
        }
    }


    private String generateAlias(){
        var sb = new StringBuilder(ALIAS_LENGTH);
        for(int i = 0; i < ALIAS_LENGTH; i++){
            sb.append((char) (Math.random() * 52 + 'a'));
        }
        return sb.toString();
    }

}
