package androidx.compose.ui;

import androidx.compose.ui.SessionMutex;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SessionMutex$withSessionCancellingPrevious$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ AtomicReference<SessionMutex.Session<Object>> $arg0;
    final /* synthetic */ Function2 $session;
    final /* synthetic */ Function1 $sessionInitializer;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionMutex$withSessionCancellingPrevious$2(Function1 function1, AtomicReference<SessionMutex.Session<Object>> atomicReference, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$sessionInitializer = function1;
        this.$arg0 = atomicReference;
        this.$session = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SessionMutex$withSessionCancellingPrevious$2 sessionMutex$withSessionCancellingPrevious$2 = new SessionMutex$withSessionCancellingPrevious$2(this.$sessionInitializer, this.$arg0, this.$session, continuation);
        sessionMutex$withSessionCancellingPrevious$2.L$0 = obj;
        return sessionMutex$withSessionCancellingPrevious$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SessionMutex$withSessionCancellingPrevious$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0057, code lost:
    
        if (kotlinx.coroutines.JobKt.cancelAndJoin(r8, r7) == r0) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L27
            if (r1 == r4) goto L1f
            if (r1 != r3) goto L17
            java.lang.Object r0 = r7.L$0
            androidx.compose.ui.SessionMutex$Session r0 = (androidx.compose.ui.SessionMutex.Session) r0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L15
            goto L6a
        L15:
            r8 = move-exception
            goto L72
        L17:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1f:
            java.lang.Object r1 = r7.L$0
            androidx.compose.ui.SessionMutex$Session r1 = (androidx.compose.ui.SessionMutex.Session) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5a
        L27:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
            androidx.compose.ui.SessionMutex$Session r1 = new androidx.compose.ui.SessionMutex$Session
            kotlin.coroutines.CoroutineContext r5 = r8.getCoroutineContext()
            kotlinx.coroutines.Job r5 = kotlinx.coroutines.JobKt.getJob(r5)
            kotlin.jvm.functions.Function1 r6 = r7.$sessionInitializer
            java.lang.Object r8 = r6.mo779invoke(r8)
            r1.<init>(r5, r8)
            java.util.concurrent.atomic.AtomicReference<androidx.compose.ui.SessionMutex$Session<java.lang.Object>> r8 = r7.$arg0
            java.lang.Object r8 = r8.getAndSet(r1)
            androidx.compose.ui.SessionMutex$Session r8 = (androidx.compose.ui.SessionMutex.Session) r8
            if (r8 == 0) goto L5a
            kotlinx.coroutines.Job r8 = r8.job
            if (r8 == 0) goto L5a
            r7.L$0 = r1
            r7.label = r4
            java.lang.Object r8 = kotlinx.coroutines.JobKt.cancelAndJoin(r8, r7)
            if (r8 != r0) goto L5a
            goto L68
        L5a:
            kotlin.jvm.functions.Function2 r8 = r7.$session     // Catch: java.lang.Throwable -> L70
            java.lang.Object r4 = r1.value     // Catch: java.lang.Throwable -> L70
            r7.L$0 = r1     // Catch: java.lang.Throwable -> L70
            r7.label = r3     // Catch: java.lang.Throwable -> L70
            java.lang.Object r8 = r8.invoke(r4, r7)     // Catch: java.lang.Throwable -> L70
            if (r8 != r0) goto L69
        L68:
            return r0
        L69:
            r0 = r1
        L6a:
            java.util.concurrent.atomic.AtomicReference<androidx.compose.ui.SessionMutex$Session<java.lang.Object>> r7 = r7.$arg0
            r7.compareAndSet(r0, r2)
            return r8
        L70:
            r8 = move-exception
            r0 = r1
        L72:
            java.util.concurrent.atomic.AtomicReference<androidx.compose.ui.SessionMutex$Session<java.lang.Object>> r7 = r7.$arg0
            r7.compareAndSet(r0, r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.SessionMutex$withSessionCancellingPrevious$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
