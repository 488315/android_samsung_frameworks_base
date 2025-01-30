package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository$special$$inlined$flatMapLatest$4", m277f = "FullMobileConnectionRepository.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class FullMobileConnectionRepository$special$$inlined$flatMapLatest$4 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public FullMobileConnectionRepository$special$$inlined$flatMapLatest$4(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FullMobileConnectionRepository$special$$inlined$flatMapLatest$4 fullMobileConnectionRepository$special$$inlined$flatMapLatest$4 = new FullMobileConnectionRepository$special$$inlined$flatMapLatest$4((Continuation) obj3);
        fullMobileConnectionRepository$special$$inlined$flatMapLatest$4.L$0 = (FlowCollector) obj;
        fullMobileConnectionRepository$special$$inlined$flatMapLatest$4.L$1 = obj2;
        return fullMobileConnectionRepository$special$$inlined$flatMapLatest$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            StateFlow isEmergencyOnly = ((MobileConnectionRepository) this.L$1).isEmergencyOnly();
            this.label = 1;
            if (FlowKt.emitAll(this, isEmergencyOnly, flowCollector) == coroutineSingletons) {
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
