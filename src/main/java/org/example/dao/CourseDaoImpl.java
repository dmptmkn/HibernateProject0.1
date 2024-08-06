package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.graph.GraphSemantic;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements CourseDao {

    private static CourseDaoImpl instance;
    private static SessionFactory sessionFactory = ConnectionManager.getConnection();

    private static final String FIND_ALL_QUERY = """
            from Course
            """;
    private static final String GET_ALL_NAMES_QUERY = """
            select name
            """ + FIND_ALL_QUERY;

    public static CourseDaoImpl getInstance() {
        if (instance == null) {
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    @BatchSize(size = 4)
    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class).list();
        }
    }

    public List<Course> findAllWithTeacher() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withTeacher"))
                    .list();
        }
    }

    public Course findByIdWithTeacher(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Map<String, Object> properties = Map.of(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withTeacher"));
            session.find(Course.class, id, properties);
            return session.find(Course.class, id, properties);
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }
}
