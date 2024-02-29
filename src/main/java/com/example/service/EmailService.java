package com.example.service;

import com.example.Entity.FilesEntity;
import com.example.Entity.Mail;
import com.example.Repository.FilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

    public String sendEmailWithHtmlTemplateWithAttachment(Mail mail)  {
        try {
            long id = 1;
            FilesEntity files = filesRepo.findById(id).orElse(null);
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(formEmail);
            helper.setTo(mail.getToArray());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getHtmlText(), true);
            Map<String, String> attachmentsMap = mail.getAttachmentsMap();
            FileSystemResource fsr = null;
//            if (attachmentsMap != null) {
//                for (Map.Entry<String, String> entry : attachmentsMap.entrySet()) {
//                    fsr = new FileSystemResource(new File(entry.getValue()));
//                    helper.addAttachment(entry.getKey(), fsr);
//                }

//            }
            if(files.getFileDate() != null) {
                File file = new File("basha.text");
                FileOutputStream fileOuputStream = new FileOutputStream(file);
                fileOuputStream.write(files.getFileDate());
                helper.addAttachment("basha.text", file);
            }
            sender.send(message);
        } catch (Exception e) {
            return "Email is not send.";
        }

        return "Email Send Successfull.";
    }
}
