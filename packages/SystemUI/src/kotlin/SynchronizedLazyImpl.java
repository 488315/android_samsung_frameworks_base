package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class SynchronizedLazyImpl<T> implements Lazy, Serializable {
    private volatile Object _value;
    private Function0 initializer;
    private final Object lock;

    public SynchronizedLazyImpl(Function0 function0, Object obj) {
        this.initializer = function0;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        this.lock = obj == null ? this : obj;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    @Override // kotlin.Lazy
    public final Object getValue() {
        Object obj;
        Object obj2 = this._value;
        UNINITIALIZED_VALUE uninitialized_value = UNINITIALIZED_VALUE.INSTANCE;
        if (obj2 != uninitialized_value) {
            return obj2;
        }
        synchronized (this.lock) {
            obj = this._value;
            if (obj == uninitialized_value) {
                Function0 function0 = this.initializer;
                function0.getClass();
                obj = function0.invoke();
                this._value = obj;
                this.initializer = null;
            }
        }
        return obj;
    }

    @Override // kotlin.Lazy
    public final boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }

    public final String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    public /* synthetic */ SynchronizedLazyImpl(Function0 function0, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, (i & 2) != 0 ? null : obj);
    }
}
