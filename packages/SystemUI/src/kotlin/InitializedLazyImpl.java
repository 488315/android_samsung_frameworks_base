package kotlin;

import java.io.Serializable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class InitializedLazyImpl<T> implements Lazy, Serializable {
    private final T value;

    public InitializedLazyImpl(T t) {
        this.value = t;
    }

    @Override // kotlin.Lazy
    public final Object getValue() {
        return this.value;
    }

    @Override // kotlin.Lazy
    public final boolean isInitialized() {
        throw null;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
