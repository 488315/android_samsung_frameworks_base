package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$femtoCellIndicatorId$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$femtoCellIndicatorId$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$femtoCellIndicatorId$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation<? super MobileIconInteractorImpl$femtoCellIndicatorId$1> continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        MobileIconInteractorImpl$femtoCellIndicatorId$1 mobileIconInteractorImpl$femtoCellIndicatorId$1 = new MobileIconInteractorImpl$femtoCellIndicatorId$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$femtoCellIndicatorId$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$femtoCellIndicatorId$1.L$0 = (MobileServiceState) obj2;
        return mobileIconInteractorImpl$femtoCellIndicatorId$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        MobileServiceState mobileServiceState = (MobileServiceState) this.L$0;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        int i = 0;
        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_FEMTOCELL_ICON, mobileIconInteractorImpl.slotId, new Object[0]) && z) {
            if (mobileServiceState.femtoCellIndicator == 1) {
                i = R.drawable.stat_sys_signal_roam_femtocell;
            }
        }
        return new Integer(i);
    }
}
