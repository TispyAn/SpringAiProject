package com.tispy.an;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {

    @Resource
    private ChatClient dashScopeChatClient;

    @Resource
    private VectorStore vectorStore;

    @GetMapping(value = "/chat", produces = "text/plain; charset=UTF-8")
   public String generation(@RequestParam("userInput") String userInput) {
        //发起聊天请求并处理响应
        return dashScopeChatClient.prompt()
                .user(userInput)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }
}
