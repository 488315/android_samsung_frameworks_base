package android.os;


public interface OutcomeReceiver<R, E extends Throwable> {
    void onResult(R r);

    default void onError(E error) {}
}
