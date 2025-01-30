package kotlin;

import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
