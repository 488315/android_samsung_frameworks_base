package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.doze.util.BurnInHelperKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class BurnInInteractor$burnInOffset$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isXAxis;
    final /* synthetic */ int $maxBurnInOffsetPixels;
    int label;
    final /* synthetic */ BurnInInteractor this$0;

    public BurnInInteractor$burnInOffset$1$1(BurnInInteractor burnInInteractor, int i, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = burnInInteractor;
        this.$maxBurnInOffsetPixels = i;
        this.$isXAxis = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BurnInInteractor$burnInOffset$1$1(this.this$0, this.$maxBurnInOffsetPixels, this.$isXAxis, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BurnInInteractor$burnInOffset$1$1) create(Long.valueOf(((Number) obj).longValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BurnInInteractor burnInInteractor = this.this$0;
        int i = this.$maxBurnInOffsetPixels;
        boolean z = this.$isXAxis;
        burnInInteractor.burnInHelperWrapper.getClass();
        return new Integer((int) (BurnInHelperKt.getBurnInOffset(i, z) * 1.0f));
    }
}
