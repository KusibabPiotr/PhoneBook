package io;

import java.io.PrintStream;

public class ConsolePrinter {
    private PrintStream ps = new PrintStream(System.out);

    public void printLine(String text){
        ps.println(text);
    }
}
