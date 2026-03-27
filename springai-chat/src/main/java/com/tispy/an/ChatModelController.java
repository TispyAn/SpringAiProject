package com.tispy.an;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/model")
public class ChatModelController {

    @Resource
    private ChatModel chatModel;

    @GetMapping("/chatModel01")
    public String chatModel01(@RequestParam("message") String message) {
        return chatModel.call(message);
    }

    @GetMapping("/chatModel02")
    public String chatModel02(@RequestParam("message") String message) {
        ChatResponse call = chatModel.call(
                new Prompt(
                        message,
                        OpenAiChatOptions.builder()
                                //可以更换成其他大模型
                                .model("deepseek-chat")
                                .temperature(0.8)
                                .build()
                )
        );
        return call.getResult().getOutput().getContent();
    }

    @GetMapping("/prompt")
    public String prompt(@RequestParam("name") String name, @RequestParam("voice") String voice) {
        String userText = "给我推荐北京的至少三种美食";
        UserMessage userMessage = new UserMessage(userText);
        String systemText = "你是一个美食咨询助手，可以帮助人们查询美食信息。你的名字是{name}，" +
                "你应该用你的名字和{voice}的饮食习惯回复用户的请求。";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        //替换占位符
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        //获取响应结果
        List<Generation> results = chatModel.call(prompt).getResults();
        return results.stream().map(x -> x.getOutput().getContent()).collect(Collectors.joining(""));
    }

}
