package org.example.dao;

import org.example.entity.Course;
import org.example.entity.Subscription;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {

    List<Subscription> getStudentInfo(Course course);
}
