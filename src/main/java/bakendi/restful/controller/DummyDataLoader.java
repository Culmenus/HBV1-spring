package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class DummyDataLoader {
    private ForumService forumService;
    private ThreadService threadService;
    private UserService userService;

    @Autowired
    public DummyDataLoader(ForumService forumService, ThreadService threadService, UserService userService) {
        this.forumService = forumService;
        this.threadService = threadService;
        this.userService = userService;
    }


    @PostMapping("/initdummy")
    public void forums() {
        User user1 = new User("Danni", "pword", "user@user.is", UserRole.ROLE_USER);
        User user2 = new User("Jon", "pword2", "admin@user.is", UserRole.ROLE_ADMIN);
        userService.save(user1);
        userService.save(user2);
        Forum forum1 = new Forum("TÖL104G", "Stærðfræðimynstur");
        forumService.save(forum1);
        forumService.save(new Forum("9+10", "21"));
        forumService.save(new Forum("TÖL101G", "Tölvunarfræði 1"));
        threadService.save(new Thread(user1));
        threadService.save(new Thread(forum1, "profid madur", "pls er eg sa eini", user1));
    }

    @PostMapping("/datatest")
    public void getHugboForums() throws IOException, CsvException {
        String root = System.getProperty("user.dir");
        String fileName = "Kennsluskra.csv";
        String filePath = root+File.separator+"src"+File.separator+"main"+File.separator+"csv"+File.separator+fileName;
        System.out.println(filePath);

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.skip(1);
            List<String[]> r = reader.readAll();
            r.forEach(x -> {
                String[] data = x[0].split(";");

                String courseId = data[0];
                String courseName = data[1];
                String ECTs = data[2];
                String semester = data[3];
                String gradLevel = data[4];
                String infoLink = data [5];

                String description = "Kennslumisseri: "+semester+"\n"+
                                        "Stig: "+gradLevel+"\n"+
                                        "Námskeiðið er "+ECTs+" einingar."+"\n"+
                                        "Nánari upplýsingar má finn hér: "+ infoLink;

                Forum tempCourseForum = new Forum(courseId, courseName);
                tempCourseForum.setDescription(description);
                forumService.save(tempCourseForum);
            });
        }
    }
}