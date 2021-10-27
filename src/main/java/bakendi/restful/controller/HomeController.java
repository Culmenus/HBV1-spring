package bakendi.restful.controller;

import bakendi.restful.service.ForumService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HomeController {


    @GetMapping("/")
    public void HomeController(HttpServletResponse response) throws IOException {

        // logged in logic?
        //response.sendRedirect("/login");

        // for now
        response.sendRedirect("/forums");

    }

    @GetMapping("/error")
    public void ErrorHandler(HttpServletResponse response) throws IOException {
        response.sendError(0, "Error occurred"  );
    }
}
