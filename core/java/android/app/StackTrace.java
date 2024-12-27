package android.app;

public class StackTrace extends Exception {
    public StackTrace(String message) {
        super(message);
    }

    public StackTrace(String message, Throwable innerStackTrace) {
        super(message, innerStackTrace);
    }
}
