package com.android.server.location.gnss.hal;

import android.location.GnssCapabilities;
import com.android.internal.util.FunctionalUtils;
import com.android.server.location.gnss.GnssPowerStats;
import com.android.server.location.gnss.hal.GnssNative;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda31 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda31(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                GnssNative gnssNative = (GnssNative) this.f$0;
                GnssCapabilities gnssCapabilities = (GnssCapabilities) this.f$1;
                GnssCapabilities gnssCapabilities2 = (GnssCapabilities) this.f$2;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$onCapabilitiesChanged$16(gnssCapabilities, gnssCapabilities2);
                break;
            default:
                ((Executor) this.f$0).execute(new GnssNative$$ExternalSyntheticLambda24(0, (GnssNative.PowerStatsCallback) this.f$1, (GnssPowerStats) this.f$2));
                break;
        }
    }
}
