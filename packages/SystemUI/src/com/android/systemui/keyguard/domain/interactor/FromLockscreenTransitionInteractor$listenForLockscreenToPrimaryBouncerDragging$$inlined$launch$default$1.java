package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

public final class FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ Ref$ObjectRef $transitionId$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    public FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1(String str, Continuation continuation, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef ref$ObjectRef) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$transitionId$inlined = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1 fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1(this.$spanName, continuation, this.this$0, this.$transitionId$inlined);
        fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1.L$0 = obj;
        return fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r13.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L27
            if (r1 != r2) goto L1f
            int r0 = r13.I$0
            boolean r1 = r13.Z$0
            java.lang.Object r2 = r13.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r13 = r13.L$0
            com.android.app.tracing.coroutines.TraceData r13 = (com.android.app.tracing.coroutines.TraceData) r13
            kotlin.ResultKt.throwOnFailure(r14)     // Catch: java.lang.Throwable -> L1c
            goto L95
        L1c:
            r14 = move-exception
            goto Lad
        L1f:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L27:
            kotlin.ResultKt.throwOnFailure(r14)
            java.lang.Object r14 = r13.L$0
            kotlinx.coroutines.CoroutineScope r14 = (kotlinx.coroutines.CoroutineScope) r14
            java.lang.String r14 = r13.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L43
            if (r4 == 0) goto L41
            goto L43
        L41:
            java.lang.String r14 = "<none>"
        L43:
            if (r1 == 0) goto L48
            r1.beginSpan(r14)
        L48:
            if (r4 == 0) goto L53
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L54
        L53:
            r5 = 0
        L54:
            if (r4 == 0) goto L59
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r14)
        L59:
            com.android.systemui.util.kotlin.Utils$Companion r6 = com.android.systemui.util.kotlin.Utils.Companion     // Catch: java.lang.Throwable -> La4
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r7 = r13.this$0     // Catch: java.lang.Throwable -> La4
            com.android.systemui.shade.data.repository.ShadeRepository r8 = r7.shadeRepository     // Catch: java.lang.Throwable -> La4
            com.android.systemui.shade.data.repository.ShadeRepositoryImpl r8 = (com.android.systemui.shade.data.repository.ShadeRepositoryImpl) r8     // Catch: java.lang.Throwable -> Lab
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r8.legacyShadeExpansion     // Catch: java.lang.Throwable -> Lab
            kotlinx.coroutines.flow.Flow r9 = r7.startedKeyguardTransitionStep     // Catch: java.lang.Throwable -> La4
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r10 = r7.transitionInteractor     // Catch: java.lang.Throwable -> La4
            kotlinx.coroutines.flow.ReadonlyStateFlow r10 = r10.currentTransitionInfoInternal     // Catch: java.lang.Throwable -> La4
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r7 = r7.keyguardInteractor     // Catch: java.lang.Throwable -> La4
            kotlinx.coroutines.flow.StateFlow r11 = r7.statusBarState     // Catch: java.lang.Throwable -> La4
            kotlinx.coroutines.flow.StateFlow r12 = r7.isKeyguardDismissible     // Catch: java.lang.Throwable -> La4
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            kotlinx.coroutines.flow.Flow r6 = r6.sample(r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> La4
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1 r7 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1     // Catch: java.lang.Throwable -> La4
            kotlin.jvm.internal.Ref$ObjectRef r8 = r13.$transitionId$inlined     // Catch: java.lang.Throwable -> La4
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor r9 = r13.this$0     // Catch: java.lang.Throwable -> La4
            r7.<init>(r8, r9)     // Catch: java.lang.Throwable -> La4
            r13.L$0 = r1     // Catch: java.lang.Throwable -> La4
            r13.L$1 = r14     // Catch: java.lang.Throwable -> La4
            r13.Z$0 = r4     // Catch: java.lang.Throwable -> La4
            r13.I$0 = r5     // Catch: java.lang.Throwable -> La4
            r13.label = r2     // Catch: java.lang.Throwable -> La4
            java.lang.Object r13 = r6.collect(r7, r13)     // Catch: java.lang.Throwable -> La4
            if (r13 != r0) goto L92
            return r0
        L92:
            r13 = r1
            r1 = r4
            r0 = r5
        L95:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1c
            if (r1 == 0) goto L9c
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9c:
            if (r13 == 0) goto La1
            r13.endSpan()
        La1:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        La4:
            r14 = move-exception
        La5:
            r13 = r1
            r1 = r4
            r0 = r5
            goto Lad
        La9:
            r14 = r13
            goto La5
        Lab:
            r13 = move-exception
            goto La9
        Lad:
            if (r1 == 0) goto Lb2
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        Lb2:
            if (r13 == 0) goto Lb7
            r13.endSpan()
        Lb7:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
