package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class AodAlphaViewModel$alpha$8 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ float F$2;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public AodAlphaViewModel$alpha$8(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        float floatValue = ((Number) obj3).floatValue();
        float floatValue2 = ((Number) obj4).floatValue();
        float floatValue3 = ((Number) obj5).floatValue();
        AodAlphaViewModel$alpha$8 aodAlphaViewModel$alpha$8 = new AodAlphaViewModel$alpha$8((Continuation) obj6);
        aodAlphaViewModel$alpha$8.L$0 = (FlowCollector) obj;
        aodAlphaViewModel$alpha$8.L$1 = (TransitionStep) obj2;
        aodAlphaViewModel$alpha$8.F$0 = floatValue;
        aodAlphaViewModel$alpha$8.F$1 = floatValue2;
        aodAlphaViewModel$alpha$8.F$2 = floatValue3;
        return aodAlphaViewModel$alpha$8.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            TransitionStep transitionStep = (TransitionStep) this.L$1;
            float f = this.F$0;
            float f2 = this.F$1;
            float f3 = this.F$2;
            KeyguardState keyguardState = transitionStep.to;
            KeyguardState keyguardState2 = KeyguardState.GONE;
            if (keyguardState != keyguardState2) {
                KeyguardState keyguardState3 = transitionStep.from;
                if (keyguardState3 == keyguardState2 && keyguardState == KeyguardState.AOD) {
                    Float f4 = new Float(f);
                    this.L$0 = null;
                    this.label = 2;
                    if (flowCollector.emit(f4, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else if (keyguardState3 == keyguardState2 && keyguardState == KeyguardState.DOZING) {
                    Float f5 = new Float(f2);
                    this.L$0 = null;
                    this.label = 3;
                    if (flowCollector.emit(f5, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    Flags.migrateClocksToBlueprint();
                    Float f6 = new Float(f3);
                    this.L$0 = null;
                    this.label = 4;
                    if (flowCollector.emit(f6, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else if (transitionStep.value == 1.0f) {
                Float f7 = new Float(0.0f);
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(f7, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
