package androidx.room.coroutines;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ConnectionPool extends AutoCloseable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RollbackException extends Throwable {
        private final Object result;

        public RollbackException(Object obj) {
            this.result = obj;
        }

        public final Object getResult() {
            return this.result;
        }
    }

    Object useConnection(boolean z, Function2 function2, ContinuationImpl continuationImpl);
}
