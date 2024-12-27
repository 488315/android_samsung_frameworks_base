package com.android.server.power.stats;

import android.os.PersistableBundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WifiPowerStatsLayout extends PowerStatsLayout {
    public int mDeviceActiveTimePosition;
    public int mDeviceBasicScanTimePosition;
    public int mDeviceBatchedScanTimePosition;
    public int mDeviceIdleTimePosition;
    public int mDeviceRxTimePosition;
    public int mDeviceScanTimePosition;
    public int mDeviceTxTimePosition;
    public boolean mPowerReportingSupported;
    public int mUidBatchScanTimePosition;
    public int mUidRxBytesPosition;
    public int mUidRxPacketsPosition;
    public int mUidScanTimePosition;
    public int mUidTxBytesPosition;
    public int mUidTxPacketsPosition;

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void fromExtras(PersistableBundle persistableBundle) {
        super.fromExtras(persistableBundle);
        this.mPowerReportingSupported = persistableBundle.getBoolean("prs");
        this.mDeviceRxTimePosition = persistableBundle.getInt("dt-rx");
        this.mDeviceTxTimePosition = persistableBundle.getInt("dt-tx");
        this.mDeviceScanTimePosition = persistableBundle.getInt("dt-scan");
        this.mDeviceBasicScanTimePosition = persistableBundle.getInt("dt-basic-scan");
        this.mDeviceBatchedScanTimePosition = persistableBundle.getInt("dt-batch-scan");
        this.mDeviceIdleTimePosition = persistableBundle.getInt("dt-idle");
        this.mDeviceActiveTimePosition = persistableBundle.getInt("dt-on");
        this.mUidRxBytesPosition = persistableBundle.getInt("urxb");
        this.mUidTxBytesPosition = persistableBundle.getInt("utxb");
        this.mUidRxPacketsPosition = persistableBundle.getInt("urxp");
        this.mUidTxPacketsPosition = persistableBundle.getInt("utxp");
        this.mUidScanTimePosition = persistableBundle.getInt("ut-scan");
        this.mUidBatchScanTimePosition = persistableBundle.getInt("ut-bscan");
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void toExtras(PersistableBundle persistableBundle) {
        super.toExtras(persistableBundle);
        persistableBundle.putBoolean("prs", this.mPowerReportingSupported);
        persistableBundle.putInt("dt-rx", this.mDeviceRxTimePosition);
        persistableBundle.putInt("dt-tx", this.mDeviceTxTimePosition);
        persistableBundle.putInt("dt-scan", this.mDeviceScanTimePosition);
        persistableBundle.putInt("dt-basic-scan", this.mDeviceBasicScanTimePosition);
        persistableBundle.putInt("dt-batch-scan", this.mDeviceBatchedScanTimePosition);
        persistableBundle.putInt("dt-idle", this.mDeviceIdleTimePosition);
        persistableBundle.putInt("dt-on", this.mDeviceActiveTimePosition);
        persistableBundle.putInt("urxb", this.mUidRxBytesPosition);
        persistableBundle.putInt("utxb", this.mUidTxBytesPosition);
        persistableBundle.putInt("urxp", this.mUidRxPacketsPosition);
        persistableBundle.putInt("utxp", this.mUidTxPacketsPosition);
        persistableBundle.putInt("ut-scan", this.mUidScanTimePosition);
        persistableBundle.putInt("ut-bscan", this.mUidBatchScanTimePosition);
    }
}
