package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Student;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentDaoImpl implements StudentDao {

    private static StudentDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Student
            """;
    private static final String GET_ALL_NAMES_QUERY = """
            select name from Student
            """;
    private static final String GET_TOTAL_COUNT_QUERY = """
            select count(*) from Student
            """;
    private static final String GET_AVERAGE_AGE_QUERY = """
            select avg(age) from Student
            """;

    public static StudentDaoImpl getInstance() {
        if (instance == null) {
            instance = new StudentDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Student> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Student.class).list();
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }

    @Override
    public Long getTotalCount() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_TOTAL_COUNT_QUERY, Long.class).getSingleResult();
        }
    }

    @Override
    public Integer getAverageAge() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_AVERAGE_AGE_QUERY, Double.class).getSingleResult().intValue();
        }
    }
}
