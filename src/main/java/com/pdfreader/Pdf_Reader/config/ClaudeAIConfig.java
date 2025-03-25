package com.pdfreader.Pdf_Reader.config;



import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClaudeAIConfig {

    @Bean
    public AnthropicApi anthropicApi(){
        return new AnthropicApi("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");
    }
}
