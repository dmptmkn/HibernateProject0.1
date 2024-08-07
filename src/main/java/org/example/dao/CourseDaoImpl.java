package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.util.ConnectionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

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

    public List<Course> findAllWithDurationGT(Integer duration) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);
            JpaRoot<Course> root = query.from(Course.class);

            query.select(root).where(builder.gt(root.get("duration"), duration));
            return session.createQuery(query).list();
        }
    }

    public List<Course> findAllWithTeacherAgeGTAndDurationLT(Integer teacherAge, Integer duration) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Course> query = builder.createQuery(Course.class);
            JpaRoot<Course> root = query.from(Course.class);

            query.select(root).where(builder.and(
                    builder.gt(root.get("teacher").get("age"), teacherAge)),
                    builder.lt(root.get("duration"), duration));

            return session.createQuery(query).list();
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_NAMES_QUERY, String.class).list();
        }
    }
}
