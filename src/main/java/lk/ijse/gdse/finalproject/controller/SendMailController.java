package lk.ijse.gdse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {
    public TextField txtSubject;
    public TextArea txtArea;
    @Setter
    private String studentEmail;

    public void sendUsingGmailOnAction(ActionEvent actionEvent) {
        if (studentEmail == null) {
            return;
        }
        final String FROM = "ireshChathuranga63309@gmail.com";
        String subject = txtSubject.getText();
        String body = txtArea.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }
        sendEmailWithGmail(FROM, studentEmail, subject, body);
    }
    private void sendEmailWithGmail(String from, String to, String subject, String messageBody){
        String PASSWORD = "hxwu zfvw nbll eiir";
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            final String FROM = "ireshChathuranga63309@gmail.com";
            String PASSWORD = "hxwu zfvw nbll eiir";
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);

            message.setText(messageBody);

            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }
}
