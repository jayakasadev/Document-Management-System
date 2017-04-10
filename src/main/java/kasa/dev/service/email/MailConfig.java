package kasa.dev.service.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private String host = "smtp.gmail.com";

    private int port = 587;

    /**
     * Bean Sets up the JailMailSender to be used by the EmailService
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("kasa288@gmail.com");
        mailSender.setPassword("npjcxyerlzxybvyn");
        mailSender.setJavaMailProperties(getMailProperties());

        return mailSender;
    }

    /**
     * Private method to setup the Properties object for sending an email
     *
     * @return Properties
     */
    private Properties getMailProperties(){
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");

        return props;
    }

}
