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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class InternetTileViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetTileViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetTileViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, InternetTileViewModel internetTileViewModel) {
        super(3, continuation);
        this.this$0 = internetTileViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetTileViewModel$special$$inlined$flatMapLatest$2 internetTileViewModel$special$$inlined$flatMapLatest$2 = new InternetTileViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        internetTileViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        internetTileViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return internetTileViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) this.L$1;
            if (mobileIconInteractor == null) {
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            } else {
                MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconInteractorImpl.isRoaming, mobileIconInteractorImpl.networkTypeIconGroup, new InternetTileViewModel$mobileDataContentName$1$1(this.this$0, null));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowCollector) == coroutineSingletons) {
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
