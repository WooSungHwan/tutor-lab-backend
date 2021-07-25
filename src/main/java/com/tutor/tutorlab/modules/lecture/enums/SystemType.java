package com.tutor.tutorlab.modules.lecture.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tutor.tutorlab.modules.base.Enumerable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemType implements Enumerable {

    ONLINE("ONLINE", "온라인"),
    OFFLINE("OFFLINE", "오프라인");

    private String type;
    private String name;

    public static SystemType find(String type) {
        return Enumerable.find(type, values());
    }

    @JsonCreator
    public static SystemType findToNull(String type) {
        return Enumerable.findToNull(type, values());
    }
}
