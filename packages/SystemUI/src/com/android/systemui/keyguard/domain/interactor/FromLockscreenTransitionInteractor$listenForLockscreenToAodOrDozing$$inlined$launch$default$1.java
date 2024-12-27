package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1(String str, Continuation continuation, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1 fromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1(this.$spanName, continuation, this.this$0);
        fromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1.L$0 = obj;
        return fromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r8.I$0
            boolean r1 = r8.Z$0
            java.lang.Object r2 = r8.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r8 = r8.L$0
            com.android.app.tracing.coroutines.TraceData r8 = (com.android.app.tracing.coroutines.TraceData) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L1b
            goto L73
        L1b:
            r9 = move-exception
            goto L86
        L1e:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L26:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            java.lang.String r9 = r8.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r9 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r9)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r9)
        L58:
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r6 = r8.this$0     // Catch: java.lang.Throwable -> L82
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$1$1 r7 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$1$1     // Catch: java.lang.Throwable -> L82
            r7.<init>()     // Catch: java.lang.Throwable -> L82
            r8.L$0 = r1     // Catch: java.lang.Throwable -> L82
            r8.L$1 = r9     // Catch: java.lang.Throwable -> L82
            r8.Z$0 = r4     // Catch: java.lang.Throwable -> L82
            r8.I$0 = r5     // Catch: java.lang.Throwable -> L82
            r8.label = r2     // Catch: java.lang.Throwable -> L82
            java.lang.Object r8 = r6.listenForSleepTransition(r7, r8)     // Catch: java.lang.Throwable -> L82
            if (r8 != r0) goto L70
            return r0
        L70:
            r8 = r1
            r1 = r4
            r0 = r5
        L73:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L7a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L7a:
            if (r8 == 0) goto L7f
            r8.endSpan()
        L7f:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L82:
            r9 = move-exception
            r8 = r1
            r1 = r4
            r0 = r5
        L86:
            if (r1 == 0) goto L8b
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L8b:
            if (r8 == 0) goto L90
            r8.endSpan()
        L90:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
