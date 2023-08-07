package com.sms;

import com.sms.config.SmsConfig;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SmsSenderApplication {

	@Autowired
	private SmsConfig smsConfig;
	@PostConstruct
	public void initTwilio(){
		Twilio.init(smsConfig.getAccountSid(),smsConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(SmsSenderApplication.class, args);
	}

}
