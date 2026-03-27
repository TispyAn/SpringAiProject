package com.tispy.an;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    //通过构造方法来注入 ChatClient
    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "你是谁") String message) {
        //prompt: 提示词
        return this.chatClient.prompt()
                //用户输入的信息
                .user(message)
                //请求大模型
                .call()
                //返回文本
                .content();
    }
}
