package com.android.server.power.stats;

import android.os.PersistableBundle;
import android.util.Slog;
import android.util.SparseArray;

public final class MobileRadioPowerStatsLayout extends PowerStatsLayout {
    public int mDeviceCallPowerPosition;
    public int mDeviceCallTimePosition;
    public int mDeviceIdleTimePosition;
    public int mDeviceScanTimePosition;
    public int mDeviceSleepTimePosition;
    public int mStateRxTimePosition;
    public int mStateTxTimesCount;
    public int mStateTxTimesPosition;
    public int mUidRxBytesPosition;
    public int mUidRxPacketsPosition;
    public int mUidTxBytesPosition;
    public int mUidTxPacketsPosition;

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void addDeviceSectionPowerEstimate() {
        super.addDeviceSectionPowerEstimate();
        this.mDeviceCallPowerPosition = addDeviceSection(1, 2, "call-power");
    }

    public final void addRxTxTimesForRat(
            SparseArray sparseArray, int i, int i2, long j, int[] iArr) {
        if (iArr.length != this.mStateTxTimesCount) {
            Slog.wtf("MobileRadioPowerStatsLayout", "Invalid TX time array size: " + iArr.length);
            return;
        }
        if (j == 0) {
            for (int length = iArr.length - 1; length >= 0; length--) {
                if (iArr[length] == 0) {}
            }
            return;
        }
        int mapRadioAccessNetworkTypeToRadioAccessTechnology =
                MobileRadioPowerStatsCollector.mapRadioAccessNetworkTypeToRadioAccessTechnology(i);
        if (mapRadioAccessNetworkTypeToRadioAccessTechnology == 2) {
            mapRadioAccessNetworkTypeToRadioAccessTechnology |= i2 << 8;
        }
        long[] jArr = (long[]) sparseArray.get(mapRadioAccessNetworkTypeToRadioAccessTechnology);
        if (jArr == null) {
            jArr = new long[this.mStateStatsArrayLength];
            sparseArray.put(mapRadioAccessNetworkTypeToRadioAccessTechnology, jArr);
        }
        int i3 = this.mStateRxTimePosition;
        jArr[i3] = jArr[i3] + j;
        for (int i4 = this.mStateTxTimesCount - 1; i4 >= 0; i4--) {
            int i5 = this.mStateTxTimesPosition + i4;
            jArr[i5] = jArr[i5] + iArr[i4];
        }
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void fromExtras(PersistableBundle persistableBundle) {
        super.fromExtras(persistableBundle);
        this.mDeviceSleepTimePosition = persistableBundle.getInt("dt-sleep");
        this.mDeviceIdleTimePosition = persistableBundle.getInt("dt-idle");
        this.mDeviceScanTimePosition = persistableBundle.getInt("dt-scan");
        this.mDeviceCallTimePosition = persistableBundle.getInt("dt-call");
        this.mDeviceCallPowerPosition = persistableBundle.getInt("dp-call");
        this.mStateRxTimePosition = persistableBundle.getInt("srx");
        this.mStateTxTimesPosition = persistableBundle.getInt("stx");
        this.mStateTxTimesCount = persistableBundle.getInt("stxc");
        this.mUidRxBytesPosition = persistableBundle.getInt("urxb");
        this.mUidTxBytesPosition = persistableBundle.getInt("utxb");
        this.mUidRxPacketsPosition = persistableBundle.getInt("urxp");
        this.mUidTxPacketsPosition = persistableBundle.getInt("utxp");
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void toExtras(PersistableBundle persistableBundle) {
        super.toExtras(persistableBundle);
        persistableBundle.putInt("dt-sleep", this.mDeviceSleepTimePosition);
        persistableBundle.putInt("dt-idle", this.mDeviceIdleTimePosition);
        persistableBundle.putInt("dt-scan", this.mDeviceScanTimePosition);
        persistableBundle.putInt("dt-call", this.mDeviceCallTimePosition);
        persistableBundle.putInt("dp-call", this.mDeviceCallPowerPosition);
        persistableBundle.putInt("srx", this.mStateRxTimePosition);
        persistableBundle.putInt("stx", this.mStateTxTimesPosition);
        persistableBundle.putInt("stxc", this.mStateTxTimesCount);
        persistableBundle.putInt("urxb", this.mUidRxBytesPosition);
        persistableBundle.putInt("utxb", this.mUidTxBytesPosition);
        persistableBundle.putInt("urxp", this.mUidRxPacketsPosition);
        persistableBundle.putInt("utxp", this.mUidTxPacketsPosition);
    }
}
