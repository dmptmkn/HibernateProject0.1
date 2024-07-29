package org.example.printer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dao.Dao;

import java.io.PrintStream;
import java.text.ChoiceFormat;

public abstract class Printer {

    public Printer(PrintStream printStream) {
        this.printStream = printStream;
    }

    PrintStream printStream;
    public abstract void printData();

    protected String getFormattedAge(int age) {
        double[] limits = {0, 1, 2, 5};
        String[] ageForms = {"лет", "год", "года", "лет"};

        ChoiceFormat format = new ChoiceFormat(limits, ageForms);
        int rule = 11 <= (age % 100) && (age % 100) <= 14 ? age : age % 10;
        return String.valueOf(age) + ' ' + format.format(rule);
    }

}
