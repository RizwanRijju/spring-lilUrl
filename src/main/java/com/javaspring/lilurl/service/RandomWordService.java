package com.javaspring.lilurl.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RandomWordService {


    private RestTemplate restTemplate;
    private final String randomWordUrl = "https://random-word-api.herokuapp.com/word";

    public RandomWordService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getRandomWord(){
        String str =  this.restTemplate.getForObject(randomWordUrl, List.class).toString();

        //WebScrapping to get the actual random string
        return str.substring(1, str.length()-2);
    }
}
