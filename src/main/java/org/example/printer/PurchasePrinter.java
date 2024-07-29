package org.example.printer;

import org.example.dao.PurchaseDao;
import org.example.dao.PurchaseDaoImpl;
import org.example.entity.Purchase;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PurchasePrinter extends Printer {

    private PurchaseDao dao = PurchaseDaoImpl.getInstance();

    public PurchasePrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void printData() {
        List<Purchase> purchases = dao.findAll();
        printStream.println("Вывожу данные по продажам:");
        for (Purchase p : purchases) {
            String formattedPurchaseInfo = String.format("Продажа №%d. %s: %s приобрел курс «%s» за ₽%d",
                    p.getId(),
                    p.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    p.getStudentName(),
                    p.getCourseName(),
                    p.getPrice());
            printStream.println(formattedPurchaseInfo);
        }
        printStream.println();
    }
}