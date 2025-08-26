package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16 implements Function3 {
    public final /* synthetic */ MobileIconInteractorKairosImpl f$0;

    public /* synthetic */ MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16(MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl) {
        this.f$0 = mobileIconInteractorKairosImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TransactionScope transactionScope = (TransactionScope) obj;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) obj2;
        if (!((Boolean) obj3).booleanValue()) {
            return new NetworkTypeIconModel.DefaultIcon(signalIcon$MobileIconGroup);
        }
        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = this.f$0;
        int overrideFor = ((MobileIconCarrierIdOverridesImpl) mobileIconInteractorKairosImpl.carrierIdOverrides).getOverrideFor(((Number) transactionScope.sample(mobileIconInteractorKairosImpl.connectionRepository.getCarrierId())).intValue(), mobileIconInteractorKairosImpl.context.getResources(), signalIcon$MobileIconGroup.name);
        return overrideFor > 0 ? new NetworkTypeIconModel.OverriddenIcon(signalIcon$MobileIconGroup, overrideFor) : new NetworkTypeIconModel.DefaultIcon(signalIcon$MobileIconGroup);
    }
}
