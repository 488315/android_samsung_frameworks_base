package com.android.server.power.stats;

import android.os.PersistableBundle;
import android.util.Slog;

public final class CpuPowerStatsLayout extends PowerStatsLayout {
    public int mDeviceCpuTimeByClusterCount;
    public int mDeviceCpuTimeByClusterPosition;
    public int mDeviceCpuTimeByScalingStepCount;
    public int mDeviceCpuTimeByScalingStepPosition;
    public int[] mScalingStepToPowerBracketMap;
    public int mUidPowerBracketCount;
    public int mUidPowerBracketsPosition;

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void fromExtras(PersistableBundle persistableBundle) {
        super.fromExtras(persistableBundle);
        this.mDeviceCpuTimeByScalingStepPosition = persistableBundle.getInt("dt");
        this.mDeviceCpuTimeByScalingStepCount = persistableBundle.getInt("dtc");
        this.mDeviceCpuTimeByClusterPosition = persistableBundle.getInt("dc");
        this.mDeviceCpuTimeByClusterCount = persistableBundle.getInt("dcc");
        this.mUidPowerBracketsPosition = persistableBundle.getInt("ub");
        String string = persistableBundle.getString("us");
        int[] iArr = null;
        if (string != null) {
            String[] split = string.trim().split(",");
            int[] iArr2 = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                try {
                    iArr2[i] = Integer.parseInt(split[i]);
                } catch (NumberFormatException unused) {
                    Slog.wtf("PowerStatsLayout", "Invalid CSV format: ".concat(string));
                }
            }
            iArr = iArr2;
        }
        this.mScalingStepToPowerBracketMap = iArr;
        if (iArr == null) {
            this.mScalingStepToPowerBracketMap = new int[this.mDeviceCpuTimeByScalingStepCount];
        }
        this.mUidPowerBracketCount = 1;
        for (int i2 : this.mScalingStepToPowerBracketMap) {
            if (i2 >= this.mUidPowerBracketCount) {
                this.mUidPowerBracketCount = i2 + 1;
            }
        }
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void toExtras(PersistableBundle persistableBundle) {
        super.toExtras(persistableBundle);
        persistableBundle.putInt("dt", this.mDeviceCpuTimeByScalingStepPosition);
        persistableBundle.putInt("dtc", this.mDeviceCpuTimeByScalingStepCount);
        persistableBundle.putInt("dc", this.mDeviceCpuTimeByClusterPosition);
        persistableBundle.putInt("dcc", this.mDeviceCpuTimeByClusterCount);
        persistableBundle.putInt("ub", this.mUidPowerBracketsPosition);
        int[] iArr = this.mScalingStepToPowerBracketMap;
        if (iArr == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : iArr) {
            if (!sb.isEmpty()) {
                sb.append(',');
            }
            sb.append(i);
        }
        persistableBundle.putString("us", sb.toString());
    }
}
