package kotlin;

import java.io.Serializable;

/* compiled from: Lazy.kt */
/* loaded from: classes.dex */
public final class InitializedLazyImpl<T> implements Lazy<T>, Serializable {
    private final T value;

    public InitializedLazyImpl(T t) {
        this.value = t;
    }

    @Override // kotlin.Lazy
    public final T getValue() {
        throw null;
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
