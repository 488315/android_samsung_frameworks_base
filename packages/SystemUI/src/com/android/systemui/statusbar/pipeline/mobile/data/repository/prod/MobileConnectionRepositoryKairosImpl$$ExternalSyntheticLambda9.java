package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyDisplayInfo;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    Integer valueOf = Integer.valueOf(overrideNetworkType);
                    if (overrideNetworkType == 0) {
                        valueOf = null;
                    }
                    if (valueOf != null) {
                        int intValue = valueOf.intValue();
                        ((MobileMappingsProxyImpl) mobileConnectionRepositoryKairosImpl.mobileMappingsProxy).getClass();
                        return new ResolvedNetworkType.OverrideNetworkType(MobileMappings.toDisplayIconKey(intValue));
                    }
                }
                if (telephonyDisplayInfo != null) {
                    int networkType = telephonyDisplayInfo.getNetworkType();
                    Integer valueOf2 = networkType != 0 ? Integer.valueOf(networkType) : null;
                    if (valueOf2 != null) {
                        int intValue2 = valueOf2.intValue();
                        ((MobileMappingsProxyImpl) mobileConnectionRepositoryKairosImpl.mobileMappingsProxy).getClass();
                        return new ResolvedNetworkType.DefaultNetworkType(Integer.toString(intValue2));
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
