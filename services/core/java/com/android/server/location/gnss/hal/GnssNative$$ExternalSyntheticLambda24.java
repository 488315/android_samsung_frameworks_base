package com.android.server.location.gnss.hal;

import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;

import com.android.server.location.gnss.GnssPowerStats;

public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda24 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda24(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GnssNative.PowerStatsCallback powerStatsCallback =
                        (GnssNative.PowerStatsCallback) this.f$0;
                GnssPowerStats gnssPowerStats = (GnssPowerStats) this.f$1;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                powerStatsCallback.onReportPowerStats(gnssPowerStats);
                break;
            case 1:
                GnssNative.lambda$reportNavigationMessage$14(
                        (GnssNative.NavigationMessageCallbacks) this.f$0,
                        (GnssNavigationMessage) this.f$1);
                break;
            default:
                GnssNative.lambda$reportMeasurementData$11(
                        (GnssNative.MeasurementCallbacks) this.f$0,
                        (GnssMeasurementsEvent) this.f$1);
                break;
        }
    }
}
