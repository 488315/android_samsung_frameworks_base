package android.os;

public class LimitExceededException extends IllegalStateException {
    public LimitExceededException() {}

    public LimitExceededException(String message) {
        super(message);
    }
}
