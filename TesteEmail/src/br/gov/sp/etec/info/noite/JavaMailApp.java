package br.gov.sp.etec.info.noite;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class JavaMailApp {
	public void enviarEmail(Email email, String caminho) {

		JOptionPane.showMessageDialog(null, email.getEmailUsuario());
		JOptionPane.showMessageDialog(null, email.getSenhaUsuario());

		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email.getEmailUsuario(), email.getSenhaUsuario());
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);

		try {
			 MimeBodyPart mbp1 = new MimeBodyPart();

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getEmailUsuario())); // Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(email.getEmailDestinatario());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(email.getAssuntoEmail());// Assunto
			message.setText(email.getTextoEmail());
			
			FileDataSource fds = new FileDataSource(caminho);
			mbp1.setDataHandler(new DataHandler(fds));
			mbp1.setFileName(fds.getName());
			
			Multipart mp = new MimeMultipart();
		    mp.addBodyPart(mbp1);

			message.setContent(mp);

			/** Método para enviar a mensagem criada */
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Email enviado com sucesso");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}