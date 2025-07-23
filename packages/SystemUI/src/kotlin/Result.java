package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Result<T> implements Serializable {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final Object value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Failure implements Serializable {
        public final Throwable exception;

        public Failure(Throwable th) {
            this.exception = th;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof Failure) && Intrinsics.areEqual(this.exception, ((Failure) obj).exception);
        }

        public final int hashCode() {
            return this.exception.hashCode();
        }

        public final String toString() {
            return "Failure(" + this.exception + ')';
        }
    }

    static {
        new Companion(null);
    }

    private /* synthetic */ Result(Object obj) {
        this.value = obj;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Result m3421boximpl(Object obj) {
        return new Result(obj);
    }

    /* renamed from: exceptionOrNull-impl, reason: not valid java name */
    public static final Throwable m3422exceptionOrNullimpl(Object obj) {
        if (obj instanceof Failure) {
            return ((Failure) obj).exception;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Result) && Intrinsics.areEqual(this.value, ((Result) obj).value);
    }

    public final int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public final String toString() {
        Object obj = this.value;
        if (obj instanceof Failure) {
            return ((Failure) obj).toString();
        }
        return "Success(" + obj + ')';
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ Object m3423unboximpl() {
        return this.value;
    }
}
