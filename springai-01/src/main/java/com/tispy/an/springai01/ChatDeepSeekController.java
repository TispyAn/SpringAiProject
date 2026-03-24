package com.tispy.an.springai01;

import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatDeepSeekController {

    @Resource
    private OpenAiChatModel chatModel;

    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "hello") String message) {
        String response = this.chatModel.call(message);
        System.out.println("response: " + response);
        return response;
    }

}
