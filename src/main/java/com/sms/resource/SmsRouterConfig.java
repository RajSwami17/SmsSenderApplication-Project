package com.sms.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SmsRouterConfig {

    @Autowired
    private SmsOtpHandler smsOtpHandler;

    @Bean
    public RouterFunction<ServerResponse> handleSms(){
       return RouterFunctions.route()
               .POST("/router/sendOtp", smsOtpHandler::sendOtp)
               .POST("/router/validateOtp", smsOtpHandler::validateOtp)
               .build();
    }
}
