package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromAodTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1(String str, Continuation continuation, FromAodTransitionInteractor fromAodTransitionInteractor) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromAodTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1 fromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1 = new FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1(this.$spanName, continuation, this.this$0);
        fromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1.L$0 = obj;
        return fromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00c0  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            r0 = r17
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            java.lang.String r4 = "Coroutines"
            if (r2 == 0) goto L2a
            if (r2 != r3) goto L22
            int r1 = r0.I$0
            boolean r2 = r0.Z$0
            java.lang.Object r3 = r0.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r0 = r0.L$0
            r3 = r0
            com.android.app.tracing.coroutines.TraceData r3 = (com.android.app.tracing.coroutines.TraceData) r3
            kotlin.ResultKt.throwOnFailure(r18)     // Catch: java.lang.Throwable -> L1f
            goto La6
        L1f:
            r0 = move-exception
            goto Lb9
        L22:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L2a:
            kotlin.ResultKt.throwOnFailure(r18)
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            java.lang.String r2 = r0.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r5 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r5 = r5.get()
            com.android.app.tracing.coroutines.TraceData r5 = (com.android.app.tracing.coroutines.TraceData) r5
            boolean r6 = android.os.Trace.isEnabled()
            if (r5 != 0) goto L46
            if (r6 == 0) goto L44
            goto L46
        L44:
            java.lang.String r2 = "<none>"
        L46:
            if (r5 == 0) goto L4b
            r5.beginSpan(r2)
        L4b:
            if (r6 == 0) goto L56
            java.util.concurrent.ThreadLocalRandom r7 = java.util.concurrent.ThreadLocalRandom.current()
            int r7 = r7.nextInt()
            goto L57
        L56:
            r7 = 0
        L57:
            if (r6 == 0) goto L5c
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r7, r4, r2)
        L5c:
            com.android.systemui.util.kotlin.Utils$Companion r8 = com.android.systemui.util.kotlin.Utils.Companion     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r9 = r0.this$0     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.power.domain.interactor.PowerInteractor r10 = r9.powerInteractor     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.ReadonlyStateFlow r10 = r10.detailedWakefulness     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1 r11 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1
                static {
                    /*
                        com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1 r0 = new com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1) com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1.INSTANCE com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.systemui.power.shared.model.WakefulnessModel r1 = (com.android.systemui.power.shared.model.WakefulnessModel) r1
                        boolean r0 = r1.isAwake()
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$1.invoke(java.lang.Object):java.lang.Object");
                }
            }     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 r12 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1     // Catch: java.lang.Throwable -> Lb5
            r12.<init>(r10, r9, r11)     // Catch: java.lang.Throwable -> Lb5
            r9 = 50
            kotlinx.coroutines.flow.Flow r9 = kotlinx.coroutines.flow.FlowKt.debounce(r12, r9)     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r10 = r0.this$0     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.Flow r11 = r10.startedKeyguardTransitionStep     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r12 = r10.keyguardInteractor     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.StateFlow r13 = r12.biometricUnlockState     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.ReadonlyStateFlow r14 = r12.primaryBouncerShowing     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.Flow r15 = r12.isKeyguardOccluded     // Catch: java.lang.Throwable -> Lb5
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 r12 = r10.canDismissLockscreen     // Catch: java.lang.Throwable -> Lb5
            r10 = r11
            r11 = r13
            r16 = r12
            r12 = r14
            r13 = r15
            r14 = r16
            kotlinx.coroutines.flow.Flow r8 = r8.sample(r9, r10, r11, r12, r13, r14)     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$2 r9 = new com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$2     // Catch: java.lang.Throwable -> Lb5
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r10 = r0.this$0     // Catch: java.lang.Throwable -> Lb5
            r9.<init>(r10)     // Catch: java.lang.Throwable -> Lb5
            r0.L$0 = r5     // Catch: java.lang.Throwable -> Lb5
            r0.L$1 = r2     // Catch: java.lang.Throwable -> Lb5
            r0.Z$0 = r6     // Catch: java.lang.Throwable -> Lb5
            r0.I$0 = r7     // Catch: java.lang.Throwable -> Lb5
            r0.label = r3     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object r0 = r8.collect(r9, r0)     // Catch: java.lang.Throwable -> Lb5
            if (r0 != r1) goto La3
            return r1
        La3:
            r3 = r5
            r2 = r6
            r1 = r7
        La6:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1f
            if (r2 == 0) goto Lad
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r1, r4)
        Lad:
            if (r3 == 0) goto Lb2
            r3.endSpan()
        Lb2:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        Lb5:
            r0 = move-exception
            r3 = r5
            r2 = r6
            r1 = r7
        Lb9:
            if (r2 == 0) goto Lbe
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r1, r4)
        Lbe:
            if (r3 == 0) goto Lc3
            r3.endSpan()
        Lc3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
