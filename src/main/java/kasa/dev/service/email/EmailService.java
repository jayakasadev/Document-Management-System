package kasa.dev.service.email;


public interface EmailService {

    /**
     * Method that sends an email every hour of all files uploaded in the last hour.
     */
    void sendEmail();

    /**
     * Custome method for sending email when needed of all files uploaded in the last hour.
     *
     * @param email
     */
    void sendEmail(String email);
}
