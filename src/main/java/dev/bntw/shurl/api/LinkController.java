package dev.bntw.shurl.api;

import dev.bntw.shurl.api.request.link.LinkRequest;
import dev.bntw.shurl.api.response.LinkResponse;
import dev.bntw.shurl.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${shuri.api-prefix}/link")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/${shuri.api-prefix}/link/create")
    public LinkResponse createLink(@RequestBody LinkRequest linkRequest){
        var alias = linkService.createLink(linkRequest.url());
        return new LinkResponse(alias);
    }

}
