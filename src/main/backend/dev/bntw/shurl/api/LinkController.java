package dev.bntw.shurl.api;

import dev.bntw.shurl.api.request.link.LinkRequest;
import dev.bntw.shurl.api.response.LinkResponse;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.services.LinkService;
import dev.bntw.shurl.utils.JwtAuth.OptionalJwtAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${shuri.api-prefix}/link")
@Slf4j
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/create")
    public LinkResponse createLink(@OptionalJwtAuth User user, @RequestBody LinkRequest linkRequest){
        var alias = linkService.createLink(linkRequest.url(),user);
        log.info("Link created /{} for {}", alias, user == null ? "anonymous" : user.getUsername());
        return new LinkResponse(alias);
    }

}
