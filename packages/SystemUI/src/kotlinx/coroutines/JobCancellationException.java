package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class JobCancellationException extends CancellationException {
    public final transient Job _job;

    public JobCancellationException(String str, Throwable th, Job job) {
        super(str);
        this._job = job;
        if (th != null) {
            initCause(th);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JobCancellationException)) {
            return false;
        }
        JobCancellationException jobCancellationException = (JobCancellationException) obj;
        if (!Intrinsics.areEqual(jobCancellationException.getMessage(), getMessage())) {
            return false;
        }
        Object obj2 = jobCancellationException._job;
        if (obj2 == null) {
            obj2 = NonCancellable.INSTANCE;
        }
        Object obj3 = this._job;
        if (obj3 == null) {
            obj3 = NonCancellable.INSTANCE;
        }
        return Intrinsics.areEqual(obj2, obj3) && Intrinsics.areEqual(jobCancellationException.getCause(), getCause());
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    public final int hashCode() {
        String message = getMessage();
        message.getClass();
        int hashCode = message.hashCode() * 31;
        Object obj = this._job;
        if (obj == null) {
            obj = NonCancellable.INSTANCE;
        }
        int hashCode2 = (hashCode + (obj != null ? obj.hashCode() : 0)) * 31;
        Throwable cause = getCause();
        return hashCode2 + (cause != null ? cause.hashCode() : 0);
    }

    @Override // java.lang.Throwable
    public final String toString() {
        String cancellationException = super.toString();
        Object obj = this._job;
        if (obj == null) {
            obj = NonCancellable.INSTANCE;
        }
        return cancellationException + "; job=" + obj;
    }
}
