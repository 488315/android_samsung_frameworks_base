package com.android.server.location.gnss.hal;

import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.Location;
import com.android.internal.util.FunctionalUtils;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda10 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNative f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda10(GnssNative gnssNative, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssNative;
        this.f$1 = obj;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                GnssNative gnssNative = this.f$0;
                Location[] locationArr = (Location[]) this.f$1;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportLocationBatch$18(locationArr);
                break;
            case 1:
                GnssNative gnssNative2 = this.f$0;
                GnssMeasurementsEvent gnssMeasurementsEvent = (GnssMeasurementsEvent) this.f$1;
                int i2 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative2.lambda$reportMeasurementData$12(gnssMeasurementsEvent);
                break;
            case 2:
                GnssNative gnssNative3 = this.f$0;
                GnssNavigationMessage gnssNavigationMessage = (GnssNavigationMessage) this.f$1;
                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative3.lambda$reportNavigationMessage$15(gnssNavigationMessage);
                break;
            default:
                GnssNative gnssNative4 = this.f$0;
                List list = (List) this.f$1;
                int i4 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative4.lambda$reportAntennaInfo$13(list);
                break;
        }
    }
}
