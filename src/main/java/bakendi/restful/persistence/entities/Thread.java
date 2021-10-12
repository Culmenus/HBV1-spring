package bakendi.restful.persistence.entities;



import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "threads")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String title;
    private String description;
    // Arraylist???? Skv. dúdda á stackoverflow. List er prefered því það er superclass
    // og er gægt að varpa yfir í arraylist og LinkedList... Veit ekki hvort við þurfum að pæla í því.
    // Something to think about
    private List<Message> messages;

}
