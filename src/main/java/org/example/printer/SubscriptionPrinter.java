package org.example.printer;

import org.example.dao.SubscriptionDao;
import org.example.dao.SubscriptionDaoImpl;
import org.example.entity.Course;
import org.example.entity.Subscription;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

public class SubscriptionPrinter extends Printer {

    private SubscriptionDao dao = SubscriptionDaoImpl.getInstance();

    public SubscriptionPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по подпискам:");
        for (Subscription s : dao.findAll()) {
            String studentName = s.getStudent() == null ? "null" : s.getStudent().getName();
            String courseName = s.getCourse() == null ? "null" : s.getCourse().getName();
            String formattedSubscriptionInfo = String.format("%s: %s подписался на курс «%s»",
                    s.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    studentName,
                    courseName);
            printStream.println(formattedSubscriptionInfo);
        }
        printStream.println();
    }

    public void printStudentInfo(Course course) {
        StringJoiner infoJoiner = new StringJoiner(System.lineSeparator());
        infoJoiner.add("Курс «" + course.getName() + "»");
        infoJoiner.add("Информация по студентам:");

        int counter = 1;
        for (Subscription subscription : dao.getStudentInfo(course)) {
            infoJoiner.add(String.format("%d. %s, %s, подписался %s",
                    counter++,
                    subscription.getStudent().getName(),
                    getFormattedAge(subscription.getStudent().getAge()),
                    subscription.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        }
        infoJoiner.add(String.format("Итоговый доход с курса: ₽%d", course.getPrice() * counter));
        printStream.println(infoJoiner);
    }
}


