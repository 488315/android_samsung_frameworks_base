package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$networkTypeIconGroup$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    public MobileIconInteractorImpl$networkTypeIconGroup$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        MobileIconInteractorImpl$networkTypeIconGroup$1 mobileIconInteractorImpl$networkTypeIconGroup$1 = new MobileIconInteractorImpl$networkTypeIconGroup$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$networkTypeIconGroup$1.L$0 = (SignalIcon$MobileIconGroup) obj;
        mobileIconInteractorImpl$networkTypeIconGroup$1.Z$0 = booleanValue;
        return mobileIconInteractorImpl$networkTypeIconGroup$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        NetworkTypeIconModel.DefaultIcon defaultIcon;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) this.L$0;
        if (this.Z$0) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
            String str = signalIcon$MobileIconGroup.name;
            Map map = (Map) mobileDataIconResource.carrierIconOverrides.get(mobileDataIconResource.carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, mobileIconInteractorImpl.slotId, new Object[0]));
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = map != null ? (SignalIcon$MobileIconGroup) map.get(str) : null;
            if (signalIcon$MobileIconGroup2 != null) {
                return new NetworkTypeIconModel.OverriddenIcon(signalIcon$MobileIconGroup2, signalIcon$MobileIconGroup2.dataType);
            }
            defaultIcon = new NetworkTypeIconModel.DefaultIcon(signalIcon$MobileIconGroup);
        } else {
            defaultIcon = new NetworkTypeIconModel.DefaultIcon(signalIcon$MobileIconGroup);
        }
        return defaultIcon;
    }
}
