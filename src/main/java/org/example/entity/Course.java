package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

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
    @ManyToOne
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
