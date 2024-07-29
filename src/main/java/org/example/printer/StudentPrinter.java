package org.example.printer;

import org.example.dao.StudentDao;
import org.example.dao.StudentDaoImpl;
import org.example.entity.Student;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentPrinter extends Printer {

    private StudentDao dao = StudentDaoImpl.getInstance();

    public StudentPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по студентам:");
        for (Student s : dao.findAll()) {
            String formattedStudentInfo = String.format("%d. %s, %s, зарегистрирован %s",
                    s.getId(),
                    s.getName(),
                    getFormattedAge(s.getAge()),
                    s.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            printStream.println(formattedStudentInfo);
        }
    }

    public void printAllNames() {
        printStream.println("Вывожу имена студентов:");
        dao.getAllNames().forEach(printStream::println);
        printStream.println();
    }

    public void printTotalCount() {
        printStream.println("Общее количество студентов: " + dao.getTotalCount());
        printStream.println();
    }

    public void printAverageAge() {
        printStream.println("Средний возраст студентов: " + getFormattedAge(dao.getAverageAge()));
        printStream.println();
    }
}

