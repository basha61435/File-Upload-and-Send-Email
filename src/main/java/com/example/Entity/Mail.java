package com.example.Entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class Mail {
    String[] toArray;
    String subject;
    String htmlText;
    Map<String,String> inlinesMap; //adding image src
    Map<String,String> attachmentsMap; // adding attachments
    List<String> ccList;
}
