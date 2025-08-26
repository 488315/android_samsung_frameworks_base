package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2 stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2 = new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2((Continuation) obj3);
        stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MobileIconViewModelCommon mobileIconViewModelCommon = (MobileIconViewModelCommon) CollectionsKt___CollectionsKt.firstOrNull((List) this.L$1);
            if (mobileIconViewModelCommon == null || (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = mobileIconViewModelCommon.getNetworkTypeIcon()) == null) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
