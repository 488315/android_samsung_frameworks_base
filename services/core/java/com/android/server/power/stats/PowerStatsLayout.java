package com.android.server.power.stats;

import android.net.INetd;
import android.os.PersistableBundle;
import com.android.internal.os.PowerStats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PowerStatsLayout {
    public int mDeviceEnergyConsumerCount;
    public int mDeviceEnergyConsumerPosition;
    public int mDeviceStatsArrayLength;
    public int mStateStatsArrayLength;
    public int mUidEnergyConsumerCount;
    public int mUidStatsArrayLength;
    public final StringBuilder mDeviceFormat = new StringBuilder();
    public final StringBuilder mStateFormat = new StringBuilder();
    public final StringBuilder mUidFormat = new StringBuilder();
    public int mDeviceDurationPosition = -1;
    public int mDevicePowerEstimatePosition = -1;
    public int mUidDurationPosition = -1;
    public int mUidEnergyConsumerPosition = -1;
    public int mUidPowerEstimatePosition = -1;

    public PowerStatsLayout() {
    }

    public PowerStatsLayout(PowerStats.Descriptor descriptor) {
        fromExtras(descriptor.extras);
    }

    public static void appendFormat(StringBuilder sb, int i, int i2, String str, int i3) {
        if ((i3 & 2) != 0) {
            return;
        }
        if (!sb.isEmpty()) {
            sb.append(' ');
        }
        sb.append(str);
        sb.append(':');
        sb.append(i);
        if (i2 != 1) {
            sb.append('[');
            sb.append(i2);
            sb.append(']');
        }
        if ((i3 & 4) != 0) {
            sb.append('p');
        }
        if ((1 & i3) != 0) {
            sb.append('?');
        }
    }

    public final int addDeviceSection(int i, int i2, String str) {
        int i3 = this.mDeviceStatsArrayLength;
        this.mDeviceStatsArrayLength = i3 + i;
        appendFormat(this.mDeviceFormat, i3, i, str, i2);
        return i3;
    }

    public final void addDeviceSectionEnergyConsumers(int i) {
        this.mDeviceEnergyConsumerPosition = addDeviceSection(i, 1, "energy");
        this.mDeviceEnergyConsumerCount = i;
    }

    public void addDeviceSectionPowerEstimate() {
        this.mDevicePowerEstimatePosition = addDeviceSection(1, 5, "power");
    }

    public final int addUidSection(int i, int i2, String str) {
        int i3 = this.mUidStatsArrayLength;
        this.mUidStatsArrayLength = i3 + i;
        appendFormat(this.mUidFormat, i3, i, str, i2);
        return i3;
    }

    public void fromExtras(PersistableBundle persistableBundle) {
        this.mDeviceDurationPosition = persistableBundle.getInt("dd");
        this.mDeviceEnergyConsumerPosition = persistableBundle.getInt("de");
        this.mDeviceEnergyConsumerCount = persistableBundle.getInt("dec");
        this.mDevicePowerEstimatePosition = persistableBundle.getInt("dp");
        this.mUidDurationPosition = persistableBundle.getInt("ud");
        this.mUidEnergyConsumerPosition = persistableBundle.getInt("ue");
        this.mUidEnergyConsumerCount = persistableBundle.getInt("uec");
        this.mUidPowerEstimatePosition = persistableBundle.getInt(INetd.IF_STATE_UP);
    }

    public final long getConsumedEnergy(int i, long[] jArr) {
        return jArr[this.mDeviceEnergyConsumerPosition + i];
    }

    public final void setConsumedEnergy(long[] jArr, int i, long j) {
        jArr[this.mDeviceEnergyConsumerPosition + i] = j;
    }

    public final void setDevicePowerEstimate(long[] jArr, double d) {
        jArr[this.mDevicePowerEstimatePosition] = (long) (d * 1000000.0d);
    }

    public final void setUidPowerEstimate(long[] jArr, double d) {
        jArr[this.mUidPowerEstimatePosition] = (long) (d * 1000000.0d);
    }

    public void toExtras(PersistableBundle persistableBundle) {
        persistableBundle.putInt("dd", this.mDeviceDurationPosition);
        persistableBundle.putInt("de", this.mDeviceEnergyConsumerPosition);
        persistableBundle.putInt("dec", this.mDeviceEnergyConsumerCount);
        persistableBundle.putInt("dp", this.mDevicePowerEstimatePosition);
        persistableBundle.putInt("ud", this.mUidDurationPosition);
        persistableBundle.putInt("ue", this.mUidEnergyConsumerPosition);
        persistableBundle.putInt("uec", this.mUidEnergyConsumerCount);
        persistableBundle.putInt(INetd.IF_STATE_UP, this.mUidPowerEstimatePosition);
        persistableBundle.putString("format-device", this.mDeviceFormat.toString());
        persistableBundle.putString("format-state", this.mStateFormat.toString());
        persistableBundle.putString("format-uid", this.mUidFormat.toString());
    }
}
