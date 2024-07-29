package org.example.dao;

import org.example.entity.Student;

import java.util.List;

public interface StudentDao extends Dao<Student> {

    List<String> getAllNames();
    Long getTotalCount();
    Integer getAverageAge();

}
