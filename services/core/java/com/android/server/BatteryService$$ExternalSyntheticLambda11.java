package com.android.server;

import android.hardware.health.HealthInfo;
import android.os.Trace;
import com.android.server.health.Utils;
import vendor.samsung.hardware.health.SehHealthInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryService$$ExternalSyntheticLambda11 {
    public final /* synthetic */ BatteryService f$0;

    public final void update(SehHealthInfo sehHealthInfo) {
        BatteryService batteryService = this.f$0;
        batteryService.getClass();
        HealthInfo healthInfo = sehHealthInfo.aospHealthInfo;
        Trace.traceBegin(524288L, "HealthInfoUpdate");
        Trace.traceCounter(131072L, "BatteryChargeCounter", healthInfo.batteryChargeCounterUah);
        Trace.traceCounter(131072L, "BatteryCurrent", healthInfo.batteryCurrentMicroamps);
        Trace.traceCounter(131072L, "PlugType", BatteryService.plugType(sehHealthInfo));
        Trace.traceCounter(131072L, "BatteryStatus", healthInfo.batteryStatus);
        synchronized (batteryService.mLock) {
            try {
                if (batteryService.mUpdatesStopped) {
                    Utils.copySehV1Battery(batteryService.mLastSehHealthInfo, sehHealthInfo);
                } else {
                    batteryService.mHealthInfo = healthInfo;
                    batteryService.mSehHealthInfo = sehHealthInfo;
                    batteryService.processValuesLocked(false);
                    batteryService.mLock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(524288L);
    }
}
