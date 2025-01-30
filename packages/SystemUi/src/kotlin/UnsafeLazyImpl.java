package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UnsafeLazyImpl<T> implements Lazy, Serializable {
    private Object _value = UNINITIALIZED_VALUE.INSTANCE;
    private Function0 initializer;

    public UnsafeLazyImpl(Function0 function0) {
        this.initializer = function0;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    @Override // kotlin.Lazy
    public final Object getValue() {
        if (this._value == UNINITIALIZED_VALUE.INSTANCE) {
            Function0 function0 = this.initializer;
            Intrinsics.checkNotNull(function0);
            this._value = function0.invoke();
            this.initializer = null;
        }
        return this._value;
    }

    @Override // kotlin.Lazy
    public final boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }

    public final String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
