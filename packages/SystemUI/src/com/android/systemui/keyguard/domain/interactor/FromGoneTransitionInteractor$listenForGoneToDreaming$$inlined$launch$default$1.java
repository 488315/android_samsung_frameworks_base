package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1(String str, Continuation continuation, FromGoneTransitionInteractor fromGoneTransitionInteractor) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromGoneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1 fromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1 = new FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1(this.$spanName, continuation, this.this$0);
        fromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1.L$0 = obj;
        return fromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a8  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L27
            if (r1 != r2) goto L1f
            int r0 = r10.I$0
            boolean r1 = r10.Z$0
            java.lang.Object r2 = r10.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r10 = r10.L$0
            com.android.app.tracing.coroutines.TraceData r10 = (com.android.app.tracing.coroutines.TraceData) r10
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L1c
            goto L89
        L1c:
            r11 = move-exception
            goto La1
        L1f:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L27:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.CoroutineScope r11 = (kotlinx.coroutines.CoroutineScope) r11
            java.lang.String r11 = r10.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L43
            if (r4 == 0) goto L41
            goto L43
        L41:
            java.lang.String r11 = "<none>"
        L43:
            if (r1 == 0) goto L48
            r1.beginSpan(r11)
        L48:
            if (r4 == 0) goto L53
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L54
        L53:
            r5 = 0
        L54:
            if (r4 == 0) goto L59
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r11)
        L59:
            com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor r6 = r10.this$0     // Catch: java.lang.Throwable -> L98
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r7 = r6.keyguardInteractor     // Catch: java.lang.Throwable -> L98
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r7.isAbleToDream     // Catch: java.lang.Throwable -> L98
            kotlinx.coroutines.flow.StateFlow r7 = r7.isActiveDreamLockscreenHosted     // Catch: java.lang.Throwable -> L98
            com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$2 r9 = com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$2.INSTANCE     // Catch: java.lang.Throwable -> L98
            kotlinx.coroutines.flow.Flow r7 = com.android.systemui.util.kotlin.FlowKt.sample(r8, r7, r9)     // Catch: java.lang.Throwable -> L98
            com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3 r8 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3
                static {
                    /*
                        com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3 r0 = new com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3) com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3.INSTANCE com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        kotlin.Pair r1 = (kotlin.Pair) r1
                        java.lang.Object r0 = r1.component1()
                        java.lang.Boolean r0 = (java.lang.Boolean) r0
                        boolean r0 = r0.booleanValue()
                        java.lang.Object r1 = r1.component2()
                        java.lang.Boolean r1 = (java.lang.Boolean) r1
                        boolean r1 = r1.booleanValue()
                        if (r0 == 0) goto L1c
                        if (r1 != 0) goto L1c
                        r0 = 1
                        goto L1d
                    L1c:
                        r0 = 0
                    L1d:
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$3.invoke(java.lang.Object):java.lang.Object");
                }
            }     // Catch: java.lang.Throwable -> L98
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 r9 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1     // Catch: java.lang.Throwable -> L9f
            r9.<init>(r7, r6, r8)     // Catch: java.lang.Throwable -> L9f
            com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$4 r6 = new com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$1$4     // Catch: java.lang.Throwable -> L98
            com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor r7 = r10.this$0     // Catch: java.lang.Throwable -> L98
            r6.<init>()     // Catch: java.lang.Throwable -> L98
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L98
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L98
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L98
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L98
            r10.label = r2     // Catch: java.lang.Throwable -> L98
            java.lang.Object r10 = r9.collect(r6, r10)     // Catch: java.lang.Throwable -> L98
            if (r10 != r0) goto L86
            return r0
        L86:
            r10 = r1
            r1 = r4
            r0 = r5
        L89:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1c
            if (r1 == 0) goto L90
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L90:
            if (r10 == 0) goto L95
            r10.endSpan()
        L95:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L98:
            r11 = move-exception
        L99:
            r10 = r1
            r1 = r4
            r0 = r5
            goto La1
        L9d:
            r11 = r10
            goto L99
        L9f:
            r10 = move-exception
            goto L9d
        La1:
            if (r1 == 0) goto La6
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        La6:
            if (r10 == 0) goto Lab
            r10.endSpan()
        Lab:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
