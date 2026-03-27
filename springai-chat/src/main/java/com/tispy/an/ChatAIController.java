package com.tispy.an;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatAIController {

    @Resource
    private ChatClient chatClient;

	//角色预设，非流式响应
    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message") String message) {
        return chatClient.prompt().user(message).call().content();
    }

    //角色预设，流式响应
    @GetMapping(value = "/chat/stream", produces = "text/html;charset=UTF-8")
    public Flux<String> chatStream(@RequestParam(value = "message") String message) {
        return chatClient.prompt().user(message).stream().content();
    }
}
