package com.tutor.tutorlab.modules.account.controller.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String phoneNumber;
    private String email;
    private String nickname;
    private String bio;
    private String zone;
}
