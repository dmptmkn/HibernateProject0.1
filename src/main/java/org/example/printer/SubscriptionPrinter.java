package org.example.printer;

import org.example.dao.SubscriptionDao;
import org.example.dao.SubscriptionDaoImpl;
import org.example.entity.Subscription;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
}


