package com.android.systemui.keyguard.data.repository;

import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.LiftReveal;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0037  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            WakefulnessModel wakefulnessModel = (WakefulnessModel) this.L$1;
            WakeSleepReason wakeSleepReason = WakeSleepReason.POWER_BUTTON;
            if (!wakefulnessModel.isAsleep() || wakefulnessModel.lastSleepReason != wakeSleepReason) {
                boolean zIsAwake = wakefulnessModel.isAwake();
                WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastWakeReason;
                if (zIsAwake && wakeSleepReason2 == wakeSleepReason) {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = this.this$0.powerButtonRevealEffect;
                } else {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = (wakefulnessModel.isAwake() && wakeSleepReason2 == WakeSleepReason.TAP) ? this.this$0.tapRevealEffect : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(LiftReveal.INSTANCE);
                }
                this.label = 1;
                if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
