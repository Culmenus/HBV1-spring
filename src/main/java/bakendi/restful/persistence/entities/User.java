package bakendi.restful.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String username;
    private String password; // hash and salt plz.
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    private Message message;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // forumId?? //dha: nooo held svona frekar
    private List<Forum> favoriteForums;

    private UserRole userRole;

    public User(String username, String password, String email, Message message, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.message = message;
        this.userRole = userRole;
    }
    public User() {

    }

    // public addForumToFavourites/removeForumFromFavourites...


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Forum> getFavoriteForums() {
        return favoriteForums;
    }

    public void setFavoriteForums(List<Forum> favoriteForums) {
        this.favoriteForums = favoriteForums;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
