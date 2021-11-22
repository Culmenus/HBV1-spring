package bakendi.restful.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

public class UserNoPword {

    private long ID;

    private String username;
    private String email;

    private List<Message> messages;

    private List<Forum> favoriteForums;

    private List<Thread> createdThreads;

    public UserNoPword(long ID, String username, String email, List<Message> messages, List<Forum> favoriteForums, List<Thread> createdThreads, UserRole userRole) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.messages = messages;
        this.favoriteForums = favoriteForums;
        this.createdThreads = createdThreads;
        this.userRole = userRole;
    }

    private UserRole userRole;

    public UserNoPword(String username, String password, String email, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.userRole = userRole;
    }

    public UserNoPword() {

    }


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
