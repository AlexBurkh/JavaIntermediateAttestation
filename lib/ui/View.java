package lib.ui;

import java.util.List;

public interface View {
    public int readInt(String text);
    public int readInt();
    public double readDouble(String text);
    public double readDouble();
    public String readText(String text);
    public String readText();
    public void print(String text);
    public void println(String text);
    public void print(int num);
    public <T> void print(List<T> lst);
    public <T> void print(T[] arr);
}