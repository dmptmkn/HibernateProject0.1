package org.example.printer;

import org.example.dao.CourseDao;
import org.example.dao.CourseDaoImpl;
import org.example.entity.Course;

import java.io.PrintStream;
import java.util.Locale;

public class CoursePrinter extends Printer {

    private CourseDao dao = CourseDaoImpl.getInstance();

    public CoursePrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по курсам:");
        for (Course c : dao.findAllWithTeacher()) {
            String formattedCourseInfo = String.format(Locale.US, "Курс №%d «%s»\nCпециальность: %s\nОписание курса: %s\nПреподаватель: %s\nДлительность курса: %d ч.\nКоличество студентов на курсе: %d\nСтоимость курса: ₽%d (или ₽%.0f за час)",
                    c.getId(),
                    c.getName(),
                    c.getType().getDescription(),
                    c.getDescription(),
                    c.getTeacher().getName(),
                    c.getDuration(),
                    c.getStudentsCount(),
                    c.getPrice(),
                    c.getPricePerHour());
            printStream.println(formattedCourseInfo);
            printStream.println("========================================================================================");
        }
        printStream.println();
    }

    public void printAllNames() {
        printStream.println("Вывожу названия курсов:");
        dao.getAllNames().forEach(printStream::println);
        printStream.println();
    }
}
