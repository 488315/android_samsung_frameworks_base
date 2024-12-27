package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.ui.StateToValue;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AodBurnInViewModel$movement$1$6 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public AodBurnInViewModel$movement$1$6(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj4).floatValue();
        AodBurnInViewModel$movement$1$6 aodBurnInViewModel$movement$1$6 = new AodBurnInViewModel$movement$1$6((Continuation) obj6);
        aodBurnInViewModel$movement$1$6.F$0 = floatValue;
        aodBurnInViewModel$movement$1$6.L$0 = (BurnInModel) obj2;
        aodBurnInViewModel$movement$1$6.L$1 = (StateToValue) obj3;
        aodBurnInViewModel$movement$1$6.F$1 = floatValue2;
        aodBurnInViewModel$movement$1$6.L$2 = (StateToValue) obj5;
        return aodBurnInViewModel$movement$1$6.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        BurnInModel burnInModel = (BurnInModel) this.L$0;
        StateToValue stateToValue = (StateToValue) this.L$1;
        float f2 = this.F$1;
        StateToValue stateToValue2 = (StateToValue) this.L$2;
        if (stateToValue2.transitionState.isTransitioning()) {
            Float f3 = stateToValue2.value;
            if (f3 != null) {
                r4 = f3.floatValue();
            }
        } else if (stateToValue.transitionState.isTransitioning()) {
            Float f4 = stateToValue.value;
            r4 = (f4 != null ? f4.floatValue() : 0.0f) + burnInModel.translationY;
        } else {
            r4 = burnInModel.translationY + f2 + f;
        }
        return new BurnInModel(burnInModel.translationX, (int) r4, burnInModel.scale, burnInModel.scaleClockOnly);
    }
}
