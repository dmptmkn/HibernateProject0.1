package org.example.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Subscription {

    private Integer id;
    private Student studentId;
    private Course courseId;
    private LocalDate subscriptionDate;

}
