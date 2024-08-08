package org.example.dao;

import org.example.entity.Teacher;

import java.util.List;

public interface TeacherDao extends Dao<Teacher> {

    void save(Teacher teacher);
    List<Teacher> findAllWithCourses();
    Teacher findById(Integer id);
    List<String> getAllNames();
    void delete(Integer id);

}
