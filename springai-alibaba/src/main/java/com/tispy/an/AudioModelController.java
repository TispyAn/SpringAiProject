package com.tispy.an;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@RestController
public class AudioModelController {

    @Resource
    private DashScopeSpeechSynthesisModel speechSynthesisModel;

    private static final String TEXT = "你是不急，都让刘维宝贝惯了，战场抗命，多大的罪名，要是搁我陈伟头上，枪毙两次都够了";
    private static final String FILE_PATH = "C:\\Users\\60206\\Desktop";

    @GetMapping("/tts")
    public void tts() throws IOException {
        System.out.println("aaaa");
        //使用构建器模式创建 DashScopeSpeechSynthesisOptions 实例并设置参数
        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .withModel("cosyvoice-v3-flash")
                .withVoice("longhuhu_v3")
                .withSpeed(1.0)        	// 设置语速
                .withPitch(0.9)         // 设置音调
                .withVolume(60)         // 设置音量
                .build();
        SpeechSynthesisResponse response = speechSynthesisModel.call(new SpeechSynthesisPrompt(TEXT,options));
        File file = new File(FILE_PATH + "\\output.mp3");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();
            fos.write(byteBuffer.array());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        System.out.println("bbb");
    }
}
