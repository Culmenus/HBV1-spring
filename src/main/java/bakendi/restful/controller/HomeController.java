package bakendi.restful.controller;

import bakendi.restful.service.ForumService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @RequestMapping("/")
    public String HomeController() {


        return "{\"asdf\":\"fdas\"";
    }
}
