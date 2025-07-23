package androidx.compose.runtime;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Applier<N> {
    void clear();

    void down(Object obj);

    Object getCurrent();

    void insertBottomUp(int i, Object obj);

    void insertTopDown(int i, Object obj);

    void move(int i, int i2, int i3);

    void remove(int i, int i2);

    default void reuse() {
        Object current = getCurrent();
        ComposeNodeLifecycleCallback composeNodeLifecycleCallback = current instanceof ComposeNodeLifecycleCallback ? (ComposeNodeLifecycleCallback) current : null;
        if (composeNodeLifecycleCallback != null) {
            composeNodeLifecycleCallback.onReuse();
        }
    }

    void up();

    default void onEndChanges() {
    }
}
