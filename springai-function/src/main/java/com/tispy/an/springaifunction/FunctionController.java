package com.tispy.an.springaifunction;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FunctionController
 * @Description FunctionController
 * @Date 2025/10/8 9:48
 * @Version 1.0
 */
@RestController
public class FunctionController {

    @Resource
    private ChatModel chatModel;

    @GetMapping(value = "/function", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public String ragJsonText(@RequestParam(value = "message") String message) {
        return ChatClient.builder(chatModel)
                .build()
                .prompt()
                .system("""
                    您是算术计算器的代理。您能够支持加法运算、乘法运算等操作。
                    当用户询问数学计算时，您必须调用相应的函数来处理。
                    支持的函数：
                    - addOperation: 加法运算，需要两个数字参数
                    - mulOperation: 乘法运算，需要两个数字参数
                    
                    调用函数的条件：
                    1. 用户明确要求计算
                    2. 问题涉及加法或乘法
                    3. 能够从问题中提取出两个数字
                    
                    请用中文回复，并在适当的时候调用函数。
                """)
                .user(message)
                .functions("addOperation", "mulOperation")
                .call()
                .content();
    }
}
