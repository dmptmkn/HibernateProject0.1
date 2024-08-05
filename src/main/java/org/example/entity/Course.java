package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "courses")
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer duration;
    private String description;
    @Enumerated(EnumType.STRING)
    private CourseType type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @Column(name = "students_count")
    private Integer studentsCount;
    private Integer price;
    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(duration, course.duration) && Objects.equals(description, course.description) && type == course.type && Objects.equals(teacher, course.teacher) && Objects.equals(studentsCount, course.studentsCount) && Objects.equals(price, course.price) && Objects.equals(pricePerHour, course.pricePerHour);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
