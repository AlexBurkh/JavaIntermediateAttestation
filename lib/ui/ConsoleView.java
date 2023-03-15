package lib.ui;

import java.util.Scanner;

public class ConsoleView implements View{
    Scanner sc;

    public ConsoleView() {
        this.sc = new Scanner(System.in);
    }

    @Override
    public int readInt(String text) {
        print(text);
        return readInt();
    }

    @Override
    public int readInt() {
        return sc.nextInt();
    }

    @Override
    public String readText(String text) {
        print(text);
        return readText();
    }

    @Override
    public String readText() {
        return sc.nextLine();
    }

    public void print(String text) {
        System.out.print(text);
    }
    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public void print(int num) {
        System.out.println(num);
    }
}
