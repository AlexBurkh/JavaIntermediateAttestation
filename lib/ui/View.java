package lib.ui;

public interface View {
    public int readInt(String text);
    public int readInt();
    public String readText(String text);
    public String readText();
    public void print(String text);
    public void println(String text);
    public void print(int num);
}