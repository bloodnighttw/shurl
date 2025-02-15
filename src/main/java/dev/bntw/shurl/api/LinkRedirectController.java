package dev.bntw.shurl.api;

import dev.bntw.shurl.api.request.link.LinkRequest;
import dev.bntw.shurl.api.response.LinkResponse;
import dev.bntw.shurl.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class LinkRedirectController {

    private final LinkService linkService;

    @Autowired
    public LinkRedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{id}")
    @Transactional
    public void redirect(HttpServletResponse response, @PathVariable String id) throws IOException {

        var link = linkService.getLink(id);
        if (link == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.sendRedirect(link.getUrl());
    }

}
