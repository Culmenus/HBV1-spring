package bakendi.restful.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "sentBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // forumId?? //dha: nooo held svona frekar
    private List<Forum> favoriteForums;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true) // forumId?? //dha: nooo held svona frekar
    private List<Thread> createdThreads;

    private UserRole userRole;

    public User(String username, String password, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    public void addToFavorites(Forum forum) {this.favoriteForums.add(forum);}

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Thread> getCreatedThreads() {
        return createdThreads;
    }

    public void setCreatedThreads(List<Thread> createdThreads) {
        this.createdThreads = createdThreads;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", messages=" + messages +
                ", favoriteForums=" + favoriteForums +
                ", userRole=" + userRole +
                '}';
    }
}
