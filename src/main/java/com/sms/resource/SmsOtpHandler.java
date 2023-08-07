package com.sms.resource;

import com.sms.dto.SmsSendRequestDto;
import com.sms.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SmsOtpHandler {

    @Autowired
    private SmsSendService smsSendService;

    public Mono<ServerResponse> sendOtp(ServerRequest serverRequest){
        return  serverRequest.bodyToMono(SmsSendRequestDto.class)
                .flatMap(dto -> smsSendService.sendSms(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                .body(BodyInserters.fromValue(dto)));
    }

    public Mono<ServerResponse> validateOtp(ServerRequest serverRequest){
        return  serverRequest.bodyToMono(SmsSendRequestDto.class)
                .flatMap(dto -> smsSendService.validateOtp(dto.getOneTimePassword(),dto.getUserName()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                .bodyValue(dto));
    }
}
