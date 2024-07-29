package org.example.printer;

import org.example.dao.TeacherDao;
import org.example.dao.TeacherDaoImpl;
import org.example.entity.Teacher;

import java.io.PrintStream;

public class TeacherPrinter extends Printer {

    private TeacherDao dao = TeacherDaoImpl.getInstance();

    public TeacherPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по преподавателям:");
        for (Teacher t : dao.findAll()) {
            String formattedTeacherInfo = String.format("%d. %s, %s, зарплата — ₽%d",
                    t.getId(),
                    t.getName(),
                    getFormattedAge(t.getAge()),
                    t.getSalary());
            printStream.println(formattedTeacherInfo);
        }
        printStream.println();
    }
}

