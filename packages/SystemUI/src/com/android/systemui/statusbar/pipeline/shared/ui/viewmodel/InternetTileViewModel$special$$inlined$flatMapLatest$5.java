package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class InternetTileViewModel$special$$inlined$flatMapLatest$5 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetTileViewModel this$0;

    public InternetTileViewModel$special$$inlined$flatMapLatest$5(Continuation continuation, InternetTileViewModel internetTileViewModel) {
        super(3, continuation);
        this.this$0 = internetTileViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetTileViewModel$special$$inlined$flatMapLatest$5 internetTileViewModel$special$$inlined$flatMapLatest$5 = new InternetTileViewModel$special$$inlined$flatMapLatest$5((Continuation) obj3, this.this$0);
        internetTileViewModel$special$$inlined$flatMapLatest$5.L$0 = (FlowCollector) obj;
        internetTileViewModel$special$$inlined$flatMapLatest$5.L$1 = obj2;
        return internetTileViewModel$special$$inlined$flatMapLatest$5.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) this.L$1;
            Flow flow = defaultConnectionModel.ethernet.isDefault ? this.this$0.ethernetIconFlow : (defaultConnectionModel.mobile.isDefault || defaultConnectionModel.carrierMerged.isDefault) ? this.this$0.mobileIconFlow : defaultConnectionModel.wifi.isDefault ? this.this$0.wifiIconFlow : this.this$0.notConnectedFlow;
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
