package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.WakeSleepReason;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import com.android.systemui.statusbar.LiftReveal;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2", m277f = "LightRevealScrimRepository.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2(Continuation continuation, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
        super(3, continuation);
        this.this$0 = lightRevealScrimRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2 = new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0075 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0055  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            WakefulnessModel wakefulnessModel = (WakefulnessModel) this.L$1;
            WakefulnessState wakefulnessState = wakefulnessModel.state;
            boolean z2 = false;
            boolean z3 = wakefulnessState == WakefulnessState.STARTING_TO_SLEEP;
            WakeSleepReason wakeSleepReason = wakefulnessModel.lastWakeReason;
            if (!(z3 && wakeSleepReason == WakeSleepReason.POWER_BUTTON)) {
                if (!((wakefulnessState == WakefulnessState.STARTING_TO_WAKE) && wakeSleepReason == WakeSleepReason.POWER_BUTTON)) {
                    z = false;
                    if (z) {
                        if (wakefulnessState == WakefulnessState.STARTING_TO_WAKE && wakeSleepReason == WakeSleepReason.TAP) {
                            z2 = true;
                        }
                        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = z2 ? this.this$0.tapRevealEffect : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(LiftReveal.INSTANCE);
                    } else {
                        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = this.this$0.powerButtonRevealEffect;
                    }
                    this.label = 1;
                    if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            z = true;
            if (z) {
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
