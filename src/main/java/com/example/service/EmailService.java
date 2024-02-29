package com.example.service;

import com.example.Entity.FilesEntity;
import com.example.Repository.FilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private FilesRepo filesRepo;
    @Value("${spring.mail.username}")
    private String formEmail;
    public String sendSimpleMail(String toEmail) {
//        FilesEntity files = filesRepo.findById(id).orElse(null);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(formEmail);
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Testing Email");
            mailMessage.setText("It is a sample Email.");
            sender.send(mailMessage);
            return "Email Send Successfull.";
        } catch (Exception e) {
            return "Email is not send.";
        }

    }
    public String sendEmailWithAttachment(Long id, String toEmail) {
        FilesEntity files = filesRepo.findById(id).orElse(null);
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(formEmail);
            helper.setTo(toEmail);
            helper.setSubject("Testing Email");
            helper.setText("It is a sample Email.");
            File file = new File("basha.text");
            FileOutputStream fileOuputStream = new FileOutputStream(file);
            fileOuputStream.write(files.getFileDate());
            helper.addAttachment("basha.text", file);
            sender.send(message);
            return "Email Send Successfull.";
        } catch (Exception e) {
            return "Email is not send.";
        }
    }
}
