package androidx.room.coroutines;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface ConnectionPool extends AutoCloseable {

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
