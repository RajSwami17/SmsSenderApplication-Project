package com.sms.service;

import com.sms.config.SmsConfig;
import com.sms.dto.OtpStatus;
import com.sms.dto.SmsSendRequestDto;
import com.sms.dto.SmsSendResponseDto;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsSendService {
    @Autowired
    private SmsConfig smsConfig;

    Map<String,String> otpMap = new HashMap<>();
    public Mono<SmsSendResponseDto> sendSms(SmsSendRequestDto smsSendRequestDto){

        SmsSendResponseDto smsSendResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(smsSendRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(smsConfig.getTrialNumber());
            String otp = generateOtp();
            String otpMessage = "Dear Customer, Your Otp is #" + otp + "#.Class la alyavar trass honar ahe ghari jaun bs...!";

            Message message = Message.creator(
                            to,
                            from,
                            otpMessage)
                    .create();
            otpMap.put(smsSendRequestDto.getUserName(),otp);
            smsSendResponseDto = new SmsSendResponseDto(OtpStatus.DELIVERED,otpMessage);
        }
        catch (Exception e)
        {
            smsSendResponseDto = new SmsSendResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return Mono.just(smsSendResponseDto);
    }

    //to genereate six digit otp
    private String generateOtp(){

        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    //to validate the otp
    public Mono<String> validateOtp(String userInputOtp,String userName) {
    if(userInputOtp.equals(otpMap.get(userName))) {
        otpMap.remove(userName,userInputOtp);
        return Mono.just("Valid Otp, please proceed with your transaction...!");
    }
    else{
        return Mono.error(new IllegalArgumentException("Invalid Otp, please try again...!"));
    }
    }
}
