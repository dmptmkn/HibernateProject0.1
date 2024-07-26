package org.example.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Purchase {

    private Integer id;
    private String studentName;
    private String courseName;
    private Integer price;
    private LocalDate subscriptionDate;

}
