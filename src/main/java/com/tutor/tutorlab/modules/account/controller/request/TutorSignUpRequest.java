package com.tutor.tutorlab.modules.account.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TutorSignUpRequest {

    private String subjects;
    private List<CareerCreateRequest> careers;
    private List<EducationCreateRequest> educations;
    private boolean specialist;

}
