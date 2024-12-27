package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$disabledActivityIcon$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    public MobileIconInteractorImpl$disabledActivityIcon$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        MobileIconInteractorImpl$disabledActivityIcon$1 mobileIconInteractorImpl$disabledActivityIcon$1 = new MobileIconInteractorImpl$disabledActivityIcon$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$disabledActivityIcon$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$disabledActivityIcon$1.Z$1 = booleanValue2;
        return mobileIconInteractorImpl$disabledActivityIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        int i = 0;
        if (z) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, mobileIconInteractorImpl.slotId, new Object[0]) && !z2) {
                SamsungMobileIcons.Companion.getClass();
                i = SamsungMobileIcons.DISABLED_FORBIDDEN;
            }
        }
        return new Integer(i);
    }
}
