package com.ltp.gradesubmission.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "grade", uniqueConstraints ={
        @UniqueConstraint(columnNames ={"student_id", "course_id"} ) // Prevents a student getting duplicate grades for the same subject
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score", nullable = false)
    private String score;

    @ManyToOne (optional = false)// Many Grades associated to one Student
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne (optional = false)// Many Grades associated to one Course
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
