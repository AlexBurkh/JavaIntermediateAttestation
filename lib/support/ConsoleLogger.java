package lib.support;

import java.time.LocalDateTime;

public class ConsoleLogger implements Logger {
    @Override
    public void INFO(String textToLog) {
        System.out.println(" ? [" + LocalDateTime.now() + "] INFO: " + textToLog + "\n");
    }

    @Override
    public void WARNING(String textToLog) {
        System.out.println(" * [" + LocalDateTime.now() + "] WARNING: " + textToLog + "\n");
    }

    @Override
    public void ERROR(String textToLog) {
        System.out.println(" ! [" + LocalDateTime.now() + "] ERROR: " + textToLog + "\n");
    }

    @Override
    public void CRYTICAL(String textToLog) {
        System.out.println(" X [" + LocalDateTime.now() + "] CRYTICAL: " + textToLog + "\n");
    }
}
