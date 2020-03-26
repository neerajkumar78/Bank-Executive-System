package Bank;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;


class BabyOfAuthenticator extends Authenticator
{  String bankEmailId,pwd;
	public BabyOfAuthenticator(String bankEmailId,String pwd) {
		this.bankEmailId=bankEmailId;
		this.pwd=pwd;
}
	public PasswordAuthentication 
		getPasswordAuthentication()
	{
		PasswordAuthentication pa = new PasswordAuthentication(bankEmailId,pwd);
		return pa;
	}
}
public class EmailSender {
	static String bankEmailId,pwd;
	public static boolean sendMail(String email,String accountNumber) 
			throws Exception
			{
		    
			Properties p = new Properties();
			p.put("mail.smtp.host","smtp.gmail.com");
			p.put("mail.smtp.port","587");
			p.put("mail.smtp.starttls.enable","true");
			p.put("mail.smtp.auth","true");
			p.put("mail.smtp.ssl.trust","smtp.gmail.com");
			BabyOfAuthenticator baby = new BabyOfAuthenticator(bankEmailId,pwd);
			Session s = Session.getInstance(p, baby);
			MimeMessage message = new MimeMessage(s);
			message.setSubject("Do Not Reply");
			InternetAddress adrs = new InternetAddress(email);
			message.setRecipient(RecipientType.TO, 
					adrs);
			MimeBodyPart body1 = new MimeBodyPart();
			body1.setContent
			("<p><i style='color : red;'>Your Account No is: "+accountNumber+"</i></p>", 
					"text/html");
			MimeMultipart parts = new MimeMultipart();
			parts.addBodyPart(body1);
			message.setContent(parts);
			Transport.send(message);
			return(true);
			}
	public static void setMailConfig(String bankEmailId,String pwd) {
		EmailSender.bankEmailId=bankEmailId;
		EmailSender.pwd=pwd;
        
	}
}
