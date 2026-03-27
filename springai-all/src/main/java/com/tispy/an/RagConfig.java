package com.tispy.an;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration
public class RagConfig {
    @Bean
    VectorStore vectorStore(@Qualifier("dashscopeEmbeddingModel") EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();
        //提取文本内容
        String filePath = "zhangsan.txt";
        TextReader textReader = new TextReader(filePath);
        textReader.getCustomMetadata().put("filePath", filePath);
        List<Document> documents = textReader.get();
        //文本切分段落
        TokenTextSplitter splitter = new TokenTextSplitter(1200, 350, 5, 100, true);
        splitter.apply(documents);
        //添加
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }

    @Bean
    @Description("某某是否有资格面试")
    public Function<RecruitServiceFunction.Request, RecruitServiceFunction.Response> recruitServiceFunction(){
        return new RecruitServiceFunction();
    }

}
