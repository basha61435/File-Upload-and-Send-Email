package com.example.controller;

import com.example.model.ChatGPTRequestModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatGPTController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${open.api.url}")
    private String apiUrl;
    @Value("${open.api.key}")
    private String apiKey;

    @GetMapping("/chat")
    public String getChatGPTRestponse(@RequestBody ChatGPTRequestModel requestModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        HttpEntity<ChatGPTRequestModel> requestEntity = new HttpEntity<>(requestModel, headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String result;
        if( !ObjectUtils.isEmpty(responseNode)) {
            ArrayNode arrayNode = (ArrayNode) responseNode.get("choices");
            result = arrayNode.get(0).get("message").get("content").asText();
        } else {
            result = "Sorry! Unable to get the data.";
        }
        return result;

    }
}
