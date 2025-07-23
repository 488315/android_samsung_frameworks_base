package com.android.systemui.statusbar.policy;

import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.PowerUtil;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$fetchEstimate$2$callback$1;
import java.util.ArrayList;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BatteryControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryControllerImpl f$0;

    public /* synthetic */ BatteryControllerImpl$$ExternalSyntheticLambda0(BatteryControllerImpl batteryControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        int i2 = 0;
        String str = null;
        int i3 = this.$r8$classId;
        BatteryControllerImpl batteryControllerImpl = this.f$0;
        switch (i3) {
            case 0:
                synchronized (batteryControllerImpl.mEstimateLock) {
                    batteryControllerImpl.mEstimate = null;
                    batteryControllerImpl.mEstimates.getClass();
                }
                batteryControllerImpl.mFetchingEstimate = false;
                batteryControllerImpl.mMainHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, i));
                return;
            default:
                synchronized (batteryControllerImpl.mFetchCallbacks) {
                    try {
                        synchronized (batteryControllerImpl.mEstimateLock) {
                            try {
                                Estimate estimate = batteryControllerImpl.mEstimate;
                                if (estimate != null) {
                                    str = PowerUtil.getBatteryRemainingShortStringFormatted(batteryControllerImpl.mContext, estimate.estimateMillis);
                                }
                            } finally {
                            }
                        }
                        ArrayList arrayList = batteryControllerImpl.mFetchCallbacks;
                        int size = arrayList.size();
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            BatteryRepository$fetchEstimate$2$callback$1 batteryRepository$fetchEstimate$2$callback$1 = (BatteryRepository$fetchEstimate$2$callback$1) obj;
                            batteryRepository$fetchEstimate$2$callback$1.getClass();
                            int i4 = Result.$r8$clinit;
                            batteryRepository$fetchEstimate$2$callback$1.$continuation.resumeWith(str);
                        }
                        batteryControllerImpl.mFetchCallbacks.clear();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
