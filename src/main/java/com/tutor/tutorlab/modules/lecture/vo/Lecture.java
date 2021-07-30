package com.tutor.tutorlab.modules.lecture.vo;

import com.tutor.tutorlab.config.converter.enumconverter.DifficultyTypeConverter;
import com.tutor.tutorlab.config.converter.enumconverter.SystemTypeConverter;
import com.tutor.tutorlab.modules.account.vo.User;
import com.tutor.tutorlab.modules.base.BaseEntity;
import com.tutor.tutorlab.modules.lecture.enums.DifficultyType;
import com.tutor.tutorlab.modules.lecture.enums.SystemType;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "lecture_id"))
@Entity
@Table(name = "lecture")
public class Lecture extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",
                referencedColumnName = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_LECTURE_USER_ID"))
    private User user;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "sub_title", nullable = false, length = 25)
    private String subTitle;

    @Column(name = "content", nullable = false, length = 25)
    private String content;

    @Column(name = "total_time", nullable = false)
    private Integer totalTime;

    @Column(name = "pertime_cost", nullable = false)
    private Long pertimeCost;

    @Column(name = "total_cost", nullable = false)
    private Long totalCost;

    @Convert(converter = DifficultyTypeConverter.class)
    @Column(name = "difficulty_type", length = 20)
    private DifficultyType difficultyType;

    @Column(name = "is_group", nullable = false)
    private Boolean isGroup;

    @Column(name = "group_number")
    private Integer groupNumber;

    @Convert(converter = SystemTypeConverter.class)
    @Column(name = "system_type", length = 20)
    private SystemType systemType;

}
