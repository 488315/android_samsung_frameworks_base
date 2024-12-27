package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class InternetTileViewModel$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetTileViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetTileViewModel$special$$inlined$flatMapLatest$3(Continuation continuation, InternetTileViewModel internetTileViewModel) {
        super(3, continuation);
        this.this$0 = internetTileViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetTileViewModel$special$$inlined$flatMapLatest$3 internetTileViewModel$special$$inlined$flatMapLatest$3 = new InternetTileViewModel$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        internetTileViewModel$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        internetTileViewModel$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return internetTileViewModel$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) this.L$1;
            if (mobileIconInteractor == null) {
                combine = this.this$0.notConnectedFlow;
            } else {
                MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
                InternetTileViewModel internetTileViewModel = this.this$0;
                combine = FlowKt.combine(mobileIconInteractorImpl.networkName, mobileIconInteractorImpl.signalLevelIcon, internetTileViewModel.mobileDataContentName, new InternetTileViewModel$mobileIconFlow$1$1(internetTileViewModel, null));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
