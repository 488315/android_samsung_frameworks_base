package androidx.compose.ui;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
public final class SessionMutex<T> {
    public final AtomicReference currentSessionHolder;

    final class Session<T> {
        public final Job job;
        public final Object value;

        public Session(Job job, T t) {
            this.job = job;
            this.value = t;
        }
    }

    /* renamed from: getCurrentSession-impl, reason: not valid java name */
    public static final Object m354getCurrentSessionimpl(AtomicReference atomicReference) {
        Session session = (Session) atomicReference.get();
        if (session != null) {
            return session.value;
        }
        return null;
    }

    /* renamed from: withSessionCancellingPrevious-impl, reason: not valid java name */
    public static final Object m355withSessionCancellingPreviousimpl(AtomicReference atomicReference, Function1 function1, Function2 function2, ContinuationImpl continuationImpl) {
        return CoroutineScopeKt.coroutineScope(new SessionMutex$withSessionCancellingPrevious$2(function1, atomicReference, function2, null), continuationImpl);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof SessionMutex) && Intrinsics.areEqual(this.currentSessionHolder, ((SessionMutex) obj).currentSessionHolder);
    }

    public final int hashCode() {
        return this.currentSessionHolder.hashCode();
    }

    public final String toString() {
        return "SessionMutex(currentSessionHolder=" + this.currentSessionHolder + ')';
    }
}
