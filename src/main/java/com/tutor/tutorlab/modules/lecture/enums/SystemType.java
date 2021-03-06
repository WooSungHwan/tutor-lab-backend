package com.tutor.tutorlab.modules.lecture.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tutor.tutorlab.config.converter.enumconverter.EnumerableConverter;
import com.tutor.tutorlab.modules.base.Enumerable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Converter;

@Getter
@AllArgsConstructor
public enum SystemType implements Enumerable {

    ONLINE("ONLINE", "온라인"),
    OFFLINE("OFFLINE", "오프라인"),
    NEGOTIABLE("NEGOTIABLE", "장소 협의 가능");

    private String type;
    private String name;

    public static SystemType find(String type) {
        return Enumerable.find(type, values());
    }

    @JsonCreator
    public static SystemType findToNull(String type) {
        return Enumerable.findToNull(type, values());
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends EnumerableConverter<SystemType> {
        public Converter() {
            super(SystemType.class);
        }
    }
}
