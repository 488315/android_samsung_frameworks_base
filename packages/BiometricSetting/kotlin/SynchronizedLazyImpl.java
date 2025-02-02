package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LazyJVM.kt */
/* loaded from: classes.dex */
final class SynchronizedLazyImpl<T> implements Lazy<T>, Serializable {
    private Function0<? extends T> initializer;
    private volatile Object _value = UNINITIALIZED_VALUE.INSTANCE;
    private final Object lock = this;

    public SynchronizedLazyImpl(Function0 function0) {
        this.initializer = function0;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    @Override // kotlin.Lazy
    public final T getValue() {
        T t;
        T t2 = (T) this._value;
        UNINITIALIZED_VALUE uninitialized_value = UNINITIALIZED_VALUE.INSTANCE;
        if (t2 != uninitialized_value) {
            return t2;
        }
        synchronized (this.lock) {
            t = (T) this._value;
            if (t == uninitialized_value) {
                Function0<? extends T> function0 = this.initializer;
                Intrinsics.checkNotNull(function0);
                t = function0.invoke();
                this._value = t;
                this.initializer = null;
            }
        }
        return t;
    }

    public final String toString() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
