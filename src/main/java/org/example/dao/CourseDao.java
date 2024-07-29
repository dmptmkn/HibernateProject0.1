package org.example.dao;

import org.example.entity.Course;

import java.util.List;

public interface CourseDao extends Dao<Course> {

    List<String> getAllNames();

}
