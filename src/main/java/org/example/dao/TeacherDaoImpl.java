package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Teacher;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements TeacherDao {

    private static TeacherDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Teacher
            """;
    private static final String GET_ALL_NAMES_QUERY = """
            select name
            """ + FIND_ALL_QUERY;

    public static TeacherDaoImpl getInstance() {
        if (instance == null) {
            instance = new TeacherDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Teacher> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Teacher.class).list();
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }
}
