package com.android.systemui.shade.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newDisplayId;
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ ShadeDisplaysInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2(int i, ShadeDisplaysInteractor shadeDisplaysInteractor, Continuation continuation) {
        super(2, continuation);
        this.$newDisplayId = i;
        this.this$0 = shadeDisplaysInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2(this.$newDisplayId, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008b A[Catch: all -> 0x002f, TryCatch #1 {all -> 0x002f, blocks: (B:16:0x002b, B:17:0x0087, B:19:0x008b, B:20:0x0090), top: B:15:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00c0  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            java.lang.String r2 = "Timed out while waiting for onMovedToDisplay to be dispatched to the shade root view in ShadeDisplaysInteractor"
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L32
            if (r1 == r4) goto L1f
            if (r1 != r3) goto L17
            java.lang.Object r12 = r12.L$0
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r12 = (com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto Lbc
        L17:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L1f:
            int r0 = r12.I$0
            long r3 = r12.J$0
            java.lang.Object r1 = r12.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r12 = r12.L$0
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r12 = (com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor) r12
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L2f
            goto L87
        L2f:
            r12 = move-exception
            goto L9c
        L32:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.shade.ShadeTraceLogger r13 = com.android.systemui.shade.ShadeTraceLogger.INSTANCE
            r13.getClass()
            com.android.app.tracing.coroutines.TrackTracer r13 = com.android.systemui.shade.ShadeTraceLogger.t
            int r1 = r12.$newDisplayId
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r5 = r12.this$0
            long r6 = r13.traceTag
            boolean r8 = android.os.Trace.isEnabled()
            r9 = 0
            if (r8 == 0) goto La0
            int r3 = com.android.app.tracing.TraceUtils.$r8$clinit
            java.lang.String r3 = "waitForOnMovedToDisplayDispatchedToView(newDisplayId="
            java.lang.String r8 = ")"
            java.lang.String r3 = androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(r1, r3, r8)
            java.util.concurrent.ThreadLocalRandom r8 = java.util.concurrent.ThreadLocalRandom.current()
            int r8 = r8.nextInt()
            java.lang.String r13 = r13.trackName
            android.os.Trace.asyncTraceForTrackBegin(r6, r13, r3, r8)
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$Companion r3 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.Companion     // Catch: java.lang.Throwable -> L9a
            r3.getClass()     // Catch: java.lang.Throwable -> L9a
            long r10 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.TIMEOUT     // Catch: java.lang.Throwable -> L9a
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 r3 = new com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1     // Catch: java.lang.Throwable -> L9a
            r3.<init>(r5, r1, r9)     // Catch: java.lang.Throwable -> L9a
            r12.L$0 = r5     // Catch: java.lang.Throwable -> L9a
            r12.L$1 = r13     // Catch: java.lang.Throwable -> L9a
            r12.J$0 = r6     // Catch: java.lang.Throwable -> L9a
            r12.I$0 = r8     // Catch: java.lang.Throwable -> L9a
            r12.label = r4     // Catch: java.lang.Throwable -> L9a
            long r9 = kotlinx.coroutines.DelayKt.m3450toDelayMillisLRDsOJo(r10)     // Catch: java.lang.Throwable -> L9a
            java.lang.Object r12 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r9, r3, r12)     // Catch: java.lang.Throwable -> L9a
            if (r12 != r0) goto L82
            goto Lba
        L82:
            r1 = r13
            r3 = r6
            r0 = r8
            r13 = r12
            r12 = r5
        L87:
            kotlin.Unit r13 = (kotlin.Unit) r13     // Catch: java.lang.Throwable -> L2f
            if (r13 != 0) goto L90
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$Companion r13 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.Companion     // Catch: java.lang.Throwable -> L2f
            r12.errorLog(r2)     // Catch: java.lang.Throwable -> L2f
        L90:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L2f
            android.os.Trace.asyncTraceForTrackEnd(r3, r1, r0)
            goto Lc5
        L96:
            r1 = r13
            r3 = r6
            r0 = r8
            goto L9c
        L9a:
            r12 = move-exception
            goto L96
        L9c:
            android.os.Trace.asyncTraceForTrackEnd(r3, r1, r0)
            throw r12
        La0:
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$Companion r13 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.Companion
            r13.getClass()
            long r6 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.TIMEOUT
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 r13 = new com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1
            r13.<init>(r5, r1, r9)
            r12.L$0 = r5
            r12.label = r3
            long r3 = kotlinx.coroutines.DelayKt.m3450toDelayMillisLRDsOJo(r6)
            java.lang.Object r13 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r3, r13, r12)
            if (r13 != r0) goto Lbb
        Lba:
            return r0
        Lbb:
            r12 = r5
        Lbc:
            kotlin.Unit r13 = (kotlin.Unit) r13
            if (r13 != 0) goto Lc5
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$Companion r13 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.Companion
            r12.errorLog(r2)
        Lc5:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
