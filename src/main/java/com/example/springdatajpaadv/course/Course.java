package com.example.springdatajpaadv.course;

import com.example.springdatajpaadv.enrolment.Enrolment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "department",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String department;

    /* BiDirection relationship m:m */
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private List<Enrolment> enrolments;

    public void addEnrolment(Enrolment enrollment) {
        if (enrolments == null)
            enrolments = new ArrayList<>();

        if (!enrolments.contains(enrollment))
            enrolments.add(enrollment);
    }

    public void removeEnrolment(Enrolment enrolment) {
        enrolments.remove(enrolment);
    }
}
