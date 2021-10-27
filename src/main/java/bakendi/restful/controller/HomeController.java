package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HomeController {


    @RequestMapping("/")
    public String HomeController() {
        return "fuck off";
    }
}
