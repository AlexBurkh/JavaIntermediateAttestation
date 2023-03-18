package lib.support;

public interface Logger {
    public void INFO(String textToLog);

    public void WARNING(String textToLog);

    public void ERROR(String textToLog);

    public void CRYTICAL(String textToLog);
}
