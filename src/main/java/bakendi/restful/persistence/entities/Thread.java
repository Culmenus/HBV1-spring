package bakendi.restful.persistence.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "threads")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private List<long> messages;
    private Date lastUpdated;

}
