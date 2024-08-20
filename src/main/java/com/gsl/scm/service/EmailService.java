package com.gsl.scm.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();

}
