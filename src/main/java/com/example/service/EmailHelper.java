package com.example.service;

import com.example.Entity.FilesEntity;
import com.example.Entity.Mail;
import com.example.Entity.StudentDetails;
import com.example.Repository.FilesRepo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Component
public class EmailHelper {
    @Autowired
    private EmailService emailService;
    @Autowired
    private FilesRepo filesRepo;
    private String rowTemplate = "  <tr style=\"text-align: center;\">\n" +
            "    <td>%s</td>\n" +
            "    <td>%s</td>\n" +
            "    <td>%s</td>\n" +
            "    <td style=\"color: %s;\">%s</td>\n" +
            "  </tr>";
    private String[] htmlValues = new String[] {"${name}", "${email}", "${table}"};

    public String processAndSendMail(StudentDetails studentDetails) throws IOException {
        File template = ResourceUtils.getFile("classpath:templates" + File.separator +"SampleEmailTemplate.html");
        String htmlText = FileUtils.readFileToString(template, "UTF-8");
        long id = 1;
        FilesEntity entity = filesRepo.findById(id).orElse(null);
        StringBuilder builder = new StringBuilder();
        builder.append("\n"+String.format(rowTemplate, studentDetails.getId(), studentDetails.getName(), studentDetails.getAddress(), "green", "Success"));
        String [] value = new String[] {  studentDetails.getName(), studentDetails.getEmail(), builder.toString() };
        Map<String, String> attachment = new HashMap<>();
        File file = new File("basha.text");
        FileOutputStream fileOuputStream = new FileOutputStream(file);
        fileOuputStream.write(entity.getFileDate());
//        File file = File.createTempFile("basha", ".log");
        FileUtils.writeStringToFile(file, entity.getFileDate().toString(), "UTF-8");
        attachment.put(file.getName(), file.getAbsolutePath());
        Mail mail = new Mail();
        mail.setSubject(String.format("%s Details is Resister Successfully.", studentDetails.getName()));
        mail.setHtmlText(StringUtils.replaceEach(htmlText, htmlValues, value));
        mail.setToArray(new String[]{studentDetails.getEmail()});
        mail.setAttachmentsMap(attachment);
        return emailService.sendEmailWithHtmlTemplateWithAttachment(mail);
    }
}
