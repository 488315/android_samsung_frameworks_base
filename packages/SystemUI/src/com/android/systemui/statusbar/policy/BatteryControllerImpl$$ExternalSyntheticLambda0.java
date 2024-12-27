package com.android.systemui.statusbar.policy;

import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.PowerUtil;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BatteryControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryControllerImpl f$0;

    public /* synthetic */ BatteryControllerImpl$$ExternalSyntheticLambda0(BatteryControllerImpl batteryControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = null;
        int i = this.$r8$classId;
        BatteryControllerImpl batteryControllerImpl = this.f$0;
        switch (i) {
            case 0:
                synchronized (batteryControllerImpl.mEstimateLock) {
                    batteryControllerImpl.mEstimate = null;
                    batteryControllerImpl.mEstimates.getClass();
                }
                batteryControllerImpl.mFetchingEstimate = false;
                batteryControllerImpl.mMainHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, 1));
                return;
            default:
                synchronized (batteryControllerImpl.mFetchCallbacks) {
                    try {
                        synchronized (batteryControllerImpl.mEstimateLock) {
                            try {
                                Estimate estimate = batteryControllerImpl.mEstimate;
                                if (estimate != null) {
                                    PowerUtil.getBatteryRemainingShortStringFormatted(batteryControllerImpl.mContext, estimate.estimateMillis);
                                }
                            } finally {
                            }
                        }
                        Iterator it = batteryControllerImpl.mFetchCallbacks.iterator();
                        while (it.hasNext()) {
                            BatteryMeterView$$ExternalSyntheticLambda0 batteryMeterView$$ExternalSyntheticLambda0 = (BatteryMeterView$$ExternalSyntheticLambda0) it.next();
                            batteryMeterView$$ExternalSyntheticLambda0.getClass();
                            int i2 = BatteryMeterView.$r8$clinit;
                            batteryMeterView$$ExternalSyntheticLambda0.getClass();
                            obj.getClass();
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
