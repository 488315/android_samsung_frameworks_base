package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

final class SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ SharedNotificationContainerInteractor.ConfigurationBasedDimensions $configurationBasedDimensions;
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    private /* synthetic */ Object L$0;
    int label;

    public SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1(SharedNotificationContainerInteractor.ConfigurationBasedDimensions configurationBasedDimensions, Continuation continuation) {
        super(4, continuation);
        this.$configurationBasedDimensions = configurationBasedDimensions;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1 sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1 = new SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1(this.$configurationBasedDimensions, (Continuation) obj4);
        sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1.F$0 = floatValue;
        sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1.F$1 = floatValue2;
        return sharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            float f = this.F$0;
            float f2 = this.F$1;
            if (f > 0.0f || f2 > 0.0f) {
                if (this.$configurationBasedDimensions.useSplitShade) {
                    Float f3 = new Float(1.0f);
                    this.label = 1;
                    if (flowCollector.emit(f3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else if (f2 == 1.0f) {
                    Float f4 = new Float(1.0f);
                    this.label = 2;
                    if (flowCollector.emit(f4, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    Float f5 = new Float(1.0f - f2);
                    this.label = 3;
                    if (flowCollector.emit(f5, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
        } else {
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
