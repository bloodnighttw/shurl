package dev.bntw.shurl.api;

import dev.bntw.shurl.services.LinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class RootController {

    private final LinkService linkService;

    @Autowired
    public RootController(LinkService linkService) {
        this.linkService = linkService;
    }

    @RequestMapping("/{id:[^.]*}")
    @Transactional
    public String redirect(@PathVariable String id) {

        var link = linkService.getLink(id);
        if (link == null) {
            return "forward:/index.html";
        }

        return "redirect:" + link.getUrl();
    }

    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }

}
