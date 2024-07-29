package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "purchaseList")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;
    private Integer price;
    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Purchase purchase = (Purchase) o;
        return id != null && Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
