package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.kairos.util.Either;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;
import java.util.Map;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DemoMobileConnectionRepositoryKairos f$0;

    public /* synthetic */ DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34(DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos, int i) {
        this.$r8$classId = i;
        this.f$0 = demoMobileConnectionRepositoryKairos;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this.f$0;
        TransactionScope transactionScope = (TransactionScope) obj;
        switch (this.$r8$classId) {
            case 0:
                return Either.First.m2586boximpl(transactionScope.sample(demoMobileConnectionRepositoryKairos.lastMobileEvent));
            default:
                Either either = (Either) obj2;
                int i = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile = (FakeNetworkEventModel.Mobile) (either instanceof Either.First ? ((Either.First) either).value : null);
                if (mobile == null || (signalIcon$MobileIconGroup = mobile.dataType) == null) {
                    return ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE;
                }
                demoMobileConnectionRepositoryKairos.getClass();
                String str = (String) ((Map) transactionScope.sample(demoMobileConnectionRepositoryKairos.mobileMappingsReverseLookup)).get(signalIcon$MobileIconGroup);
                if (str == null) {
                    str = "dis";
                }
                return new ResolvedNetworkType.DefaultNetworkType(str);
        }
    }
}
