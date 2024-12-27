package com.android.systemui.volume.panel.ui.viewmodel;

import android.content.res.Configuration;
import android.content.res.Resources;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class VolumePanelViewModel$volumePanelState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Resources $resources;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumePanelViewModel$volumePanelState$1(Resources resources, Continuation continuation) {
        super(2, continuation);
        this.$resources = resources;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumePanelViewModel$volumePanelState$1 volumePanelViewModel$volumePanelState$1 = new VolumePanelViewModel$volumePanelState$1(this.$resources, continuation);
        volumePanelViewModel$volumePanelState$1.L$0 = obj;
        return volumePanelViewModel$volumePanelState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumePanelViewModel$volumePanelState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Configuration configuration = this.$resources.getConfiguration();
            this.label = 1;
            if (flowCollector.emit(configuration, this) == coroutineSingletons) {
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
