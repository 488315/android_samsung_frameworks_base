package android.app;

public abstract class ForegroundServiceTypeException extends ServiceStartNotAllowedException {
    public ForegroundServiceTypeException(String message) {
        super(message);
    }
}
