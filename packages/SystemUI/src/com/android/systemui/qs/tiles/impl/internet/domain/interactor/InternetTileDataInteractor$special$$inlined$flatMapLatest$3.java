package com.android.systemui.qs.tiles.impl.internet.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class InternetTileDataInteractor$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetTileDataInteractor this$0;

    public InternetTileDataInteractor$special$$inlined$flatMapLatest$3(Continuation continuation, InternetTileDataInteractor internetTileDataInteractor) {
        super(3, continuation);
        this.this$0 = internetTileDataInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetTileDataInteractor$special$$inlined$flatMapLatest$3 internetTileDataInteractor$special$$inlined$flatMapLatest$3 = new InternetTileDataInteractor$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        internetTileDataInteractor$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        internetTileDataInteractor$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return internetTileDataInteractor$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow buffer$default;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) this.L$1;
            if (mobileIconInteractor == null) {
                buffer$default = this.this$0.notConnectedFlow;
            } else {
                MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
                buffer$default = FlowKt.buffer$default(FlowKt.mapLatest(FlowKt.combine(mobileIconInteractorImpl.networkName, mobileIconInteractorImpl.signalLevelIcon, this.this$0.mobileDataContentName, new InternetTileDataInteractor$mobileIconFlow$1$1(null)), new InternetTileDataInteractor$mobileIconFlow$1$2(this.this$0, null)), -1);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, buffer$default, flowCollector) == coroutineSingletons) {
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
