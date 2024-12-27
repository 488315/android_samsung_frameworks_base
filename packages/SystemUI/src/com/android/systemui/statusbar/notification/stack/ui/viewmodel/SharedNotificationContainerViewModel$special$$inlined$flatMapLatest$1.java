package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

public final class SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SharedNotificationContainerViewModel this$0;

    public SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
        super(3, continuation);
        this.this$0 = sharedNotificationContainerViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1 sharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1 = new SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        sharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return sharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            SafeFlow combineTransform = FlowKt.combineTransform(((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.getShadeExpansion(), ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.getQsExpansion(), new SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$1$1((SharedNotificationContainerInteractor.ConfigurationBasedDimensions) this.L$1, null));
            this.label = 1;
            if (FlowKt.emitAll(this, combineTransform, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
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
