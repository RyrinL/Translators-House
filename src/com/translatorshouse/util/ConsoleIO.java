package com.translatorshouse.util;

import java.util.Scanner;

/** Thin wrapper around System.in/out so the rest of the code never touches Scanner directly. */
public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void blank() {
        System.out.println();
    }

    public String readLine() {
        if (!scanner.hasNextLine()) {
            return "quit";
        }
        return scanner.nextLine();
    }
}
