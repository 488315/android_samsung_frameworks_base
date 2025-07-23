package android.os;

import java.lang.Throwable;

/* loaded from: classes3.dex */
public interface OutcomeReceiver<R, E extends Throwable> {
    default void onError(E e) {
    }

    void onResult(R r);
}
