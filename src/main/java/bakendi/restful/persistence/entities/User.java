package bakendi.restful.persistence.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// TODO VENSLA SAMANA ENTITY, SBR. ONE TO MANY OSFRV
@Table(name = "users") //þarf að laga þetta.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password; // hash and salt plz.
    private String email;

    private List<long> favoriteForums;
    /*private Enum userRole */




}
