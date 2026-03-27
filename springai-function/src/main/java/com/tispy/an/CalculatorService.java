package com.tispy.an;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @ClassName CalculatorService
 * @Description CalculatorService
 * @Date 2025/10/8 9:45
 * @Version 1.0
 */
@Slf4j
@Configuration
public class CalculatorService {

    public record AddOperation(int a, int b) {

    }

    public record MulOperation(int m, int n) {

    }

	@Bean
    @Description("加法运算")
    public Function<AddOperation, Integer> addOperation() {
        return request -> {
            log.info("执行加法运算: {} + {} = {}", request.a, request.b, request.a + request.b);
            return request.a + request.b;  // 实际调用时执行
        };
    }

    @Bean
    @Description("乘法运算")
    public Function<MulOperation, Integer> mulOperation() {
        return request -> {
            log.info("执行乘法运算: {} * {} = {}", request.m, request.n, request.m * request.n);
            return request.m * request.n;  // 实际调用时执行
        };
    }
}
