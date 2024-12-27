package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class AodBurnInViewModel$burnIn$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ BurnInParameters $params;
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AodBurnInViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodBurnInViewModel$burnIn$2(AodBurnInViewModel aodBurnInViewModel, BurnInParameters burnInParameters, Continuation continuation) {
        super(3, continuation);
        this.this$0 = aodBurnInViewModel;
        this.$params = burnInParameters;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        AodBurnInViewModel$burnIn$2 aodBurnInViewModel$burnIn$2 = new AodBurnInViewModel$burnIn$2(this.this$0, this.$params, (Continuation) obj3);
        aodBurnInViewModel$burnIn$2.F$0 = floatValue;
        aodBurnInViewModel$burnIn$2.L$0 = (BurnInModel) obj2;
        return aodBurnInViewModel$burnIn$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ClockConfig config;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        BurnInModel burnInModel = (BurnInModel) this.L$0;
        ClockController clockController = (ClockController) this.this$0.keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        boolean z = (clockController == null || (config = clockController.getConfig()) == null || !config.getUseAlternateSmartspaceAODTransition()) && this.this$0.keyguardClockViewModel.clockSize.$$delegate_0.getValue() == ClockSize.LARGE;
        int lerp = (int) MathUtils.lerp(0, burnInModel.translationY, f);
        Flags.migrateClocksToBlueprint();
        BurnInParameters burnInParameters = this.$params;
        return new BurnInModel((int) MathUtils.lerp(0, burnInModel.translationX, f), Math.max(burnInParameters.topInset, burnInParameters.minViewY + lerp) - this.$params.minViewY, MathUtils.lerp(burnInModel.scale, 1.0f, 1.0f - f), z);
    }
}
