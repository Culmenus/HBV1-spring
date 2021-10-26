package bakendi.restful.persistence.entities;

import javax.persistence.*;


@Entity
@Table(name = "users") //þarf að laga þetta.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password; // hash and salt plz.
    private String email;

    @OneToMany(mappedBy = "") // forumId??
    private List<long> favoriteForums;
    /*private Enum userRole */




}
