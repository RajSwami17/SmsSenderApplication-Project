package com.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendRequestDto {

    private String phoneNumber;

    private String userName;

    private String oneTimePassword;
}
