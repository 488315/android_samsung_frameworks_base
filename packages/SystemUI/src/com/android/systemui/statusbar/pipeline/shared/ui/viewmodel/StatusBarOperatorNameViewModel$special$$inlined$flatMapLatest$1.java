package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ MobileIconsInteractor $mobileIconsInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, MobileIconsInteractor mobileIconsInteractor) {
        super(3, continuation);
        this.$mobileIconsInteractor$inlined = mobileIconsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1 statusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1 = new StatusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$mobileIconsInteractor$inlined);
        statusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        statusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return statusBarOperatorNameViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Integer num = (Integer) this.L$1;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = num == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : this.$mobileIconsInteractor$inlined.getMobileConnectionInteractorForSubId(num.intValue()).getCarrierName();
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
