package com.android.systemui.shade;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class SecPanelSplitHelper$2$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    int label;
    final /* synthetic */ SecPanelSplitHelper this$0;

    public SecPanelSplitHelper$2$1(SecPanelSplitHelper secPanelSplitHelper, Continuation continuation) {
        super(3, continuation);
        this.this$0 = secPanelSplitHelper;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        SecPanelSplitHelper$2$1 secPanelSplitHelper$2$1 = new SecPanelSplitHelper$2$1(this.this$0, (Continuation) obj3);
        secPanelSplitHelper$2$1.F$0 = floatValue;
        secPanelSplitHelper$2$1.F$1 = floatValue2;
        return secPanelSplitHelper$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        float f2 = this.F$1;
        SecPanelSplitHelper secPanelSplitHelper = this.this$0;
        if (secPanelSplitHelper.enabled) {
            int i = secPanelSplitHelper.currentState;
            int i2 = PanelTransitionState.$r8$clinit;
            if (i == 3 && (f > 0.0f || f2 > 0.0f)) {
                secPanelSplitHelper.slide$1(1);
            }
        }
        SecPanelSplitHelper secPanelSplitHelper2 = this.this$0;
        if (secPanelSplitHelper2.enabled && ((StatusBarStateController) secPanelSplitHelper2.statusBarStateController$delegate.getValue()).getState() == 1 && f == 0.0f && this.this$0.quickSettingsControllerImpl.getExpanded$1()) {
            this.this$0.quickSettingsControllerImpl.setExpanded(false);
        }
        return Unit.INSTANCE;
    }
}
