package com.tispy.an.springaiollama;

import jakarta.annotation.Resource;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama")
public class SpringAiOllamaController {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @GetMapping("/test")
    public String generate(@RequestParam(value = "message", defaultValue = "hello") String message) {
        String response = this.ollamaChatModel.call(message);
        System.out.println("response: " + response);
        return response;
    }
}
