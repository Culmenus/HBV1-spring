package bakendi.restful.persistence.entities;

import java.util.Date;


public class Message {
    private long id;

    // hef util, til að hafa sek
    private Date createdAt;
    private long sentById;
    private String message;
    private boolean isEdited;
}
