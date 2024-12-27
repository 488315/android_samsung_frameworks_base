package android.os;


/* loaded from: classes3.dex */
public interface OutcomeReceiver<R, E extends Throwable> {
    void onResult(R r);

    default void onError(E error) {}
}
