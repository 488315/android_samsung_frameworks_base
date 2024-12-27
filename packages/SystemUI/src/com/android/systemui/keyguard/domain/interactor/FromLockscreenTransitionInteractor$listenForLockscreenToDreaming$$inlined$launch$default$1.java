package com.android.systemui.keyguard.domain.interactor;

import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set $invalidFromStates$inlined;
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    public FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1(String str, Continuation continuation, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Set set) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$invalidFromStates$inlined = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1 fromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1(this.$spanName, continuation, this.this$0, this.$invalidFromStates$inlined);
        fromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1.L$0 = obj;
        return fromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L27
            if (r1 != r2) goto L1f
            int r0 = r11.I$0
            boolean r1 = r11.Z$0
            java.lang.Object r2 = r11.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r11 = r11.L$0
            com.android.app.tracing.coroutines.TraceData r11 = (com.android.app.tracing.coroutines.TraceData) r11
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L1c
            goto L93
        L1c:
            r12 = move-exception
            goto Lab
        L1f:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L27:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.CoroutineScope r12 = (kotlinx.coroutines.CoroutineScope) r12
            java.lang.String r12 = r11.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L43
            if (r4 == 0) goto L41
            goto L43
        L41:
            java.lang.String r12 = "<none>"
        L43:
            if (r1 == 0) goto L48
            r1.beginSpan(r12)
        L48:
            if (r4 == 0) goto L53
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L54
        L53:
            r5 = 0
        L54:
            if (r4 == 0) goto L59
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r12)
        L59:
            com.android.systemui.util.kotlin.Utils$Companion r6 = com.android.systemui.util.kotlin.Utils.Companion     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r7 = r11.this$0     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r8 = r7.keyguardInteractor     // Catch: java.lang.Throwable -> La2
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r8.isAbleToDream     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 r9 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1     // Catch: java.lang.Throwable -> La9
            r9.<init>(r8, r7)     // Catch: java.lang.Throwable -> La9
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r7 = r11.this$0     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r8 = r7.transitionInteractor     // Catch: java.lang.Throwable -> La2
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r8.currentTransitionInfoInternal     // Catch: java.lang.Throwable -> La2
            kotlinx.coroutines.flow.SharedFlow r10 = r7.finishedKeyguardState     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r7 = r7.keyguardInteractor     // Catch: java.lang.Throwable -> La2
            kotlinx.coroutines.flow.StateFlow r7 = r7.isActiveDreamLockscreenHosted     // Catch: java.lang.Throwable -> La2
            kotlinx.coroutines.flow.Flow r6 = r6.sample(r9, r8, r10, r7)     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1$1 r7 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1$1     // Catch: java.lang.Throwable -> La2
            java.util.Set r8 = r11.$invalidFromStates$inlined     // Catch: java.lang.Throwable -> La2
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r9 = r11.this$0     // Catch: java.lang.Throwable -> La2
            r7.<init>()     // Catch: java.lang.Throwable -> La2
            r11.L$0 = r1     // Catch: java.lang.Throwable -> La2
            r11.L$1 = r12     // Catch: java.lang.Throwable -> La2
            r11.Z$0 = r4     // Catch: java.lang.Throwable -> La2
            r11.I$0 = r5     // Catch: java.lang.Throwable -> La2
            r11.label = r2     // Catch: java.lang.Throwable -> La2
            java.lang.Object r11 = r6.collect(r7, r11)     // Catch: java.lang.Throwable -> La2
            if (r11 != r0) goto L90
            return r0
        L90:
            r11 = r1
            r1 = r4
            r0 = r5
        L93:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1c
            if (r1 == 0) goto L9a
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9a:
            if (r11 == 0) goto L9f
            r11.endSpan()
        L9f:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        La2:
            r12 = move-exception
        La3:
            r11 = r1
            r1 = r4
            r0 = r5
            goto Lab
        La7:
            r12 = r11
            goto La3
        La9:
            r11 = move-exception
            goto La7
        Lab:
            if (r1 == 0) goto Lb0
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        Lb0:
            if (r11 == 0) goto Lb5
            r11.endSpan()
        Lb5:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
