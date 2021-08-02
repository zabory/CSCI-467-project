package Controllers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailController {

	final private static String FROM_ADDRESS = "badcarparts@gmail.com";
	final private static String SECRET = "foeigyocwtflrlub";
	final private static String MAIL_SERVER = "smtp.gmail.com";
	final private static int MAIL_SERVER_PORT = 465;

	/**
	 * This sends an email out
	 * @param address Email address to be sent too
	 * @param sub Subject of the email
	 * @param mess Message of the email
	 */
	public static void doSendEmail(String address, String sub, String mess) {

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", MAIL_SERVER);
		properties.put("mail.smtp.port", MAIL_SERVER_PORT + "");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(FROM_ADDRESS, SECRET);

			}

		});

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_ADDRESS));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject(sub);
            message.setText(mess);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}