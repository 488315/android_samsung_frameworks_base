package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyDisplayInfo;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) obj2;
                MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = (MobileConnectionRepositoryKairosImpl) this.f$0;
                if (telephonyDisplayInfo != null) {
                    int overrideNetworkType = telephonyDisplayInfo.getOverrideNetworkType();
                    Integer numValueOf = Integer.valueOf(overrideNetworkType);
                    if (overrideNetworkType == 0) {
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        int iIntValue = numValueOf.intValue();
                        ((MobileMappingsProxyImpl) mobileConnectionRepositoryKairosImpl.mobileMappingsProxy).getClass();
                        return new ResolvedNetworkType.OverrideNetworkType(MobileMappings.toDisplayIconKey(iIntValue));
                    }
                }
                if (telephonyDisplayInfo != null) {
                    int networkType = telephonyDisplayInfo.getNetworkType();
                    Integer numValueOf2 = networkType != 0 ? Integer.valueOf(networkType) : null;
                    if (numValueOf2 != null) {
                        int iIntValue2 = numValueOf2.intValue();
                        ((MobileMappingsProxyImpl) mobileConnectionRepositoryKairosImpl.mobileMappingsProxy).getClass();
                        return new ResolvedNetworkType.DefaultNetworkType(Integer.toString(iIntValue2));
                    }
                }
                return ResolvedNetworkType.UnknownNetworkType.INSTANCE;
            case 1:
                Integer num = (Integer) ((TransactionScope) obj).sample(((MobileConnectionRepositoryKairosImpl) this.f$0).cdmaEnhancedRoamingIndicatorDisplayNumber);
                return Boolean.valueOf((num != null && num.intValue() == 0) || (num != null && num.intValue() == 2));
            default:
                SubscriptionModel subscriptionModel = (SubscriptionModel) obj2;
                return subscriptionModel != null ? new NetworkNameModel.SubscriptionDerived(subscriptionModel.carrierName) : (NetworkNameModel) this.f$0;
        }
    }
}
