package kasa.dev.service.email;


import kasa.dev.model.UploadRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ScheduledEmailService implements EmailService{

    @Autowired
    private JavaMailSender mailSender;
    private final UploadRepository uploadRepository;

    @Autowired
    public ScheduledEmailService(UploadRepository uploadRepository){
        log.info("initializing ScheduledEmailService");
        this.mailSender = mailSender;
        this.uploadRepository = uploadRepository;
    }

    private final String email = "kasa288@gmail.com";

    private final Log log = LogFactory.getLog(getClass());

    // TODO @Scheduled(cron = "0 0 0/1 * * *") vs @Scheduled(cron = "0 0 */1 * * *")
    @Override
    @Scheduled(cron = "0 0 */1 * * *")
    // @Scheduled(initialDelay = 1000, fixedRate = 15000)
    public void sendEmail() {
        log.info("Sending Email");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("New Files");
        /*
        simpleMailMessage.setText(uploadRepository.findAll().
                stream().filter(x -> x.getDateUploaded().compareTo(Timestamp.valueOf(LocalDateTime.now())) < 0).
                map(upload -> "\n" + upload.toString()).
                collect(Collectors.toList()).toString()
        );
        */
        simpleMailMessage.setText(uploadRepository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1))).toString());


        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmail(String email) {
        log.info("Sending Email to " + email);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("New Files");

        /*
        simpleMailMessage.setText(uploadRepository.findAll().
                stream().filter(x -> x.getDateUploaded().compareTo(Timestamp.valueOf(LocalDateTime.now())) < 0).
                map(upload -> "\n" + upload.toString()).
                collect(Collectors.toList()).toString()
        );
        */
        simpleMailMessage.setText(uploadRepository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1))).toString());

        mailSender.send(simpleMailMessage);
    }
}
