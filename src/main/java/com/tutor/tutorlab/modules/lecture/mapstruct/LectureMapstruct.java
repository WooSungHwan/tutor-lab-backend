package com.tutor.tutorlab.modules.lecture.mapstruct;

import com.tutor.tutorlab.config.mapstruct.MapstructConfig;
import com.tutor.tutorlab.modules.lecture.controller.response.LectureResponse;
import com.tutor.tutorlab.modules.lecture.enums.DifficultyType;
import com.tutor.tutorlab.modules.lecture.enums.SystemType;
import com.tutor.tutorlab.modules.lecture.vo.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {DifficultyType.class, SystemType.class})
public interface LectureMapstruct {

    @Mappings({
            @Mapping(target = "difficultyType", source = "difficultyType.type"),
            @Mapping(target = "difficultyName", source = "difficultyType.name"),
            @Mapping(target = "systemType", source = "systemType.type"),
            @Mapping(target = "systemName", source = "systemType.name"),
    })
    LectureResponse lectureToLectureResponse(Lecture lecture);
}
