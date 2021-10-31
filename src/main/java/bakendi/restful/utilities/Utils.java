package bakendi.restful.utilities;
import org.springframework.stereotype.Component;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
@Component
public class Utils {

    public void sendMail(String url, String to){
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "ggez@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Verification email from DILF");

            // Now set the actual message
            message.setText("Click the link below \n\n"+ url);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
