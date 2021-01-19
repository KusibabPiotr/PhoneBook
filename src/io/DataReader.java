package io;

import java.util.Scanner;

public class DataReader {
    private Scanner scanner = new Scanner(System.in);

    public String readLine(){
        return scanner.nextLine();
    }

    public int readInt(){
        return scanner.nextInt();
    }

    public void close(){
        scanner.close();
    }
}
