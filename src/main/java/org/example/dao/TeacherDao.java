package org.example.dao;

import org.example.entity.Teacher;

import java.util.List;

public interface TeacherDao extends Dao<Teacher> {

    List<String> getAllNames();

}
