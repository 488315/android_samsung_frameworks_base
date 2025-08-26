package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda28 implements Function4 {
    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
        DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) obj3;
        boolean zBooleanValue = ((Boolean) obj4).booleanValue();
        int i = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
        boolean z = defaultConnectionModel.carrierMerged.isDefault || defaultConnectionModel.wifi.isDefault || zBooleanValue;
        if ((wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) && z) {
            return Integer.valueOf(((WifiNetworkModel.CarrierMerged) wifiNetworkModel).subscriptionId);
        }
        return null;
    }
}
