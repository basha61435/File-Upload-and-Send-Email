package com.example.controller;

import com.example.Entity.FilesEntity;
import com.example.Repository.FilesRepo;
import com.example.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FilesController {
    @Autowired
    private FilesRepo filesRepo;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EmailService emailService;
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile files, @RequestParam("data") String data) throws IOException {
        FilesEntity entity;
        try {
             entity = mapper.readValue(data, FilesEntity.class);

         entity = FilesEntity.builder()
                .fileName(files.getOriginalFilename())
                .fileType(files.getContentType())
                .fileDate(files.getBytes())
                .name(entity.getName())
                .type(entity.getType())
                .build();
            entity =  filesRepo.save(entity);

        } catch (IOException e) {
            return "data is not converted";
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download1/")
                .path(String.valueOf(entity.getId()))
                .toUriString();
        return fileDownloadUri;
    }
    @GetMapping("/display/{id}")
    public ResponseEntity<Resource> display(@PathVariable("id") Long id ) {
    Optional<FilesEntity> files = null;
    files = filesRepo.findById(id);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(files.get().getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "files; filename=\""+files.get().getFileName()+"\"")
            .body(new ByteArrayResource(files.get().getFileDate()));

    }

    @GetMapping("/download1/{id}")
    public ResponseEntity<byte []> downloadFile1(@PathVariable("id") Long id ) {
        FilesEntity files = filesRepo.findById(id).get();
//        byte[] data = files.getFileDate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "data");
//        return new ResponseEntity<>(data, headers, HttpStatus.OK);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(files.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "file; filename=\""+files.getFileName()+"\"")
                .body(new ByteArrayResource(files.getFileDate()).getByteArray());

    }
    @GetMapping("/sampleEmail/{email}")
    public String sampleEmail(@PathVariable String email) {
        return emailService.sendSimpleMail(email);
    }
    @GetMapping("/sampleEmailWithAttachment/{id}/{email}")
    public String sampleEmailWithAttachment(@PathVariable Long id, @PathVariable String email) {
        return emailService.sendEmailWithAttachment(id, email);
    }
}
