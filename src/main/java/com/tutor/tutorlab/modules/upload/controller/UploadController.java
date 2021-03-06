package com.tutor.tutorlab.modules.upload.controller;

import com.tutor.tutorlab.modules.upload.controller.request.UploadImageRequest;
import com.tutor.tutorlab.modules.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uploads")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object uploadImage(@ModelAttribute @Validated UploadImageRequest param) throws Exception {
        return uploadService.uploadImage(param.getFile());
    }

}
