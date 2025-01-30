package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$forcingCellularValidation$2", m277f = "MobileIconsInteractor.kt", m278l = {267, 268, 269}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconsInteractorImpl$forcingCellularValidation$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;

    public MobileIconsInteractorImpl$forcingCellularValidation$2(Continuation<? super MobileIconsInteractorImpl$forcingCellularValidation$2> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconsInteractorImpl$forcingCellularValidation$2 mobileIconsInteractorImpl$forcingCellularValidation$2 = new MobileIconsInteractorImpl$forcingCellularValidation$2((Continuation) obj3);
        mobileIconsInteractorImpl$forcingCellularValidation$2.L$0 = (FlowCollector) obj;
        return mobileIconsInteractorImpl$forcingCellularValidation$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Boolean bool;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            Boolean bool2 = Boolean.TRUE;
            this.L$0 = flowCollector2;
            this.label = 1;
            if (flowCollector2.emit(bool2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            flowCollector = flowCollector2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                bool = Boolean.FALSE;
                this.L$0 = null;
                this.label = 3;
                if (flowCollector.emit(bool, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.L$0 = flowCollector;
        this.label = 2;
        if (DelayKt.delay(2000L, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        bool = Boolean.FALSE;
        this.L$0 = null;
        this.label = 3;
        if (flowCollector.emit(bool, this) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
