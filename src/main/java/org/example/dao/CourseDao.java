package org.example.dao;

import org.example.entity.Course;

import java.util.List;

public interface CourseDao extends Dao<Course> {

    void save(Course course);
    List<String> getAllNames();
    List<Course> findAllWithTeacher();
    List<Course> findAllWithStudents();
    List<Course> findAllWithDurationGT(Integer duration);
    List<Course> findAllWithPriceGT(Integer price);
    List<Course> findAllWithTeacherAgeGTAndDurationLT(Integer teacherAge, Integer duration);
    List<Course> findAllWithPriceBetween(Integer from, Integer to);
    List<Course> findAllWithTeacherAgeGTOrDurationGT(Integer teacherAge, Integer duration);
    List<Course> findFirstNWithMaxDuration(Integer limit);
}
