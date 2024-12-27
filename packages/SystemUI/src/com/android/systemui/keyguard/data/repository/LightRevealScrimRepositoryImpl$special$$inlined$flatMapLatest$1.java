package com.android.systemui.keyguard.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

public final class LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
        super(3, continuation);
        this.this$0 = lightRevealScrimRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 = new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005b A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5c
        Ld:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L15:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r1 = r6.L$1
            com.android.systemui.power.shared.model.WakefulnessModel r1 = (com.android.systemui.power.shared.model.WakefulnessModel) r1
            com.android.systemui.power.shared.model.WakeSleepReason r3 = com.android.systemui.power.shared.model.WakeSleepReason.POWER_BUTTON
            boolean r4 = r1.isAsleep()
            if (r4 == 0) goto L2d
            com.android.systemui.power.shared.model.WakeSleepReason r4 = r1.lastSleepReason
            if (r4 != r3) goto L2d
            goto L37
        L2d:
            boolean r4 = r1.isAwake()
            com.android.systemui.power.shared.model.WakeSleepReason r5 = r1.lastWakeReason
            if (r4 == 0) goto L3c
            if (r5 != r3) goto L3c
        L37:
            com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl r1 = r6.this$0
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r1 = r1.powerButtonRevealEffect
            goto L53
        L3c:
            com.android.systemui.power.shared.model.WakeSleepReason r3 = com.android.systemui.power.shared.model.WakeSleepReason.TAP
            boolean r1 = r1.isAwake()
            if (r1 == 0) goto L4b
            if (r5 != r3) goto L4b
            com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl r1 = r6.this$0
            com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1 r1 = r1.tapRevealEffect
            goto L53
        L4b:
            com.android.systemui.statusbar.LiftReveal r1 = com.android.systemui.statusbar.LiftReveal.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            r1 = r3
        L53:
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.emitAll(r6, r1, r7)
            if (r6 != r0) goto L5c
            return r0
        L5c:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
