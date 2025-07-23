package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
            function0.getClass();
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
