package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.util.Log;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileSignalTransitionManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileIconInteractorImpl$updateSignalTransition$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$updateSignalTransition$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(4, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        int intValue2 = ((Number) obj3).intValue();
        MobileIconInteractorImpl$updateSignalTransition$1 mobileIconInteractorImpl$updateSignalTransition$1 = new MobileIconInteractorImpl$updateSignalTransition$1(this.this$0, (Continuation) obj4);
        mobileIconInteractorImpl$updateSignalTransition$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$updateSignalTransition$1.I$0 = intValue;
        mobileIconInteractorImpl$updateSignalTransition$1.I$1 = intValue2;
        return mobileIconInteractorImpl$updateSignalTransition$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.I$0;
        int i2 = this.I$1;
        MobileSignalTransitionManager mobileSignalTransitionManager = this.this$0.mobileSignalTransition;
        mobileSignalTransitionManager.getClass();
        Log.d("MobileSignalTransitionManager", "updateSignalTransition");
        if (mobileSignalTransitionManager.isTransition || (!z && mobileSignalTransitionManager.currentState == MobileSignalTransitionManager.TransitionSignalState.NO_SERVICE)) {
            mobileSignalTransitionManager.targetSignalStrength = i;
        } else {
            mobileSignalTransitionManager.updateSignalOneLevelPerSec(i, i2, z);
        }
        return Unit.INSTANCE;
    }
}
