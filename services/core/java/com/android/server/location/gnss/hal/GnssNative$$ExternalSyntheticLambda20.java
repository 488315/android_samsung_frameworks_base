package com.android.server.location.gnss.hal;

import android.os.Binder;

import com.android.server.location.gnss.GnssPowerStats;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda20
        implements GnssNative.PowerStatsCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda20(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.PowerStatsCallback
    public final void onReportPowerStats(GnssPowerStats gnssPowerStats) {
        switch (this.$r8$classId) {
            case 0:
                GnssNative.lambda$requestPowerStatsBlocking$5(
                        (AtomicReference) this.f$0, (CountDownLatch) this.f$1, gnssPowerStats);
                break;
            default:
                Binder.withCleanCallingIdentity(
                        new GnssNative$$ExternalSyntheticLambda31(
                                (Executor) this.f$0,
                                (GnssNative.PowerStatsCallback) this.f$1,
                                gnssPowerStats,
                                1));
                break;
        }
    }
}
