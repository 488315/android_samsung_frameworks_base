package com.android.server.location.gnss;

import android.telephony.SubscriptionManager;
import com.android.server.location.gnss.GnssLocationProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GnssLocationProvider$$ExternalSyntheticLambda12(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((GnssSatelliteBlocklistHelper) obj).updateSatelliteBlocklist();
                break;
            case 1:
                TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper = (TimeDetectorNetworkTimeHelper) ((NetworkTimeHelper) obj);
                timeDetectorNetworkTimeHelper.mEnvironment.requestImmediateTimeQueryCallback(timeDetectorNetworkTimeHelper, "demandUtcTimeInjection");
                break;
            case 2:
                ((GnssLocationProvider.AnonymousClass1) obj).this$0.mGnssConfiguration.reloadGpsProperties(SubscriptionManager.getDefaultDataSubscriptionId(), false);
                break;
            default:
                GnssLocationProvider.AnonymousClass2 anonymousClass2 = (GnssLocationProvider.AnonymousClass2) obj;
                anonymousClass2.this$0.gnssConfigurationUpdateSec("SIM_SLOT_ID=" + anonymousClass2.this$0.mSimSlotId);
                break;
        }
    }
}
