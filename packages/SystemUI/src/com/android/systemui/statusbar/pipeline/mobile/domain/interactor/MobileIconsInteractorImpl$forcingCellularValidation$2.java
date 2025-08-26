package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.util.DelayableMarqueeTextView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class MobileIconsInteractorImpl$forcingCellularValidation$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;

    public MobileIconsInteractorImpl$forcingCellularValidation$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconsInteractorImpl$forcingCellularValidation$2 mobileIconsInteractorImpl$forcingCellularValidation$2 = new MobileIconsInteractorImpl$forcingCellularValidation$2((Continuation) obj3);
        mobileIconsInteractorImpl$forcingCellularValidation$2.L$0 = (FlowCollector) obj;
        return mobileIconsInteractorImpl$forcingCellularValidation$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r1.emit(r6, r5) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            Boolean bool = Boolean.TRUE;
            this.L$0 = flowCollector2;
            this.label = 1;
            if (flowCollector2.emit(bool, this) != coroutineSingletons) {
                flowCollector = flowCollector2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            Boolean bool2 = Boolean.FALSE;
            this.L$0 = null;
            this.label = 3;
        }
        this.L$0 = flowCollector;
        this.label = 2;
        if (DelayKt.delay(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, this) != coroutineSingletons) {
            Boolean bool22 = Boolean.FALSE;
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
