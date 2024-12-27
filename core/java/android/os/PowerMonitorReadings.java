package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final class PowerMonitorReadings {
    public static final int ENERGY_UNAVAILABLE = -1;
    private static final Comparator<PowerMonitor> POWER_MONITOR_COMPARATOR = Comparator.comparingInt(new ToIntFunction() { // from class: android.os.PowerMonitorReadings$$ExternalSyntheticLambda0
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            int i;
            i = ((PowerMonitor) obj).index;
            return i;
        }
    });
    private final long[] mEnergyUws;
    private final PowerMonitor[] mPowerMonitors;
    private final long[] mTimestampsMs;

    public PowerMonitorReadings(PowerMonitor[] powerMonitors, long[] energyUws, long[] timestampsMs) {
        this.mPowerMonitors = powerMonitors;
        this.mEnergyUws = energyUws;
        this.mTimestampsMs = timestampsMs;
    }

    public long getConsumedEnergy(PowerMonitor powerMonitor) {
        int offset = Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (offset >= 0) {
            return this.mEnergyUws[offset];
        }
        return -1L;
    }

    public long getTimestampMillis(PowerMonitor powerMonitor) {
        int offset = Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (offset >= 0) {
            return this.mTimestampsMs[offset];
        }
        return 0L;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" monitors: [");
        for (int i = 0; i < this.mPowerMonitors.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mPowerMonitors[i].getName()).append(" = ").append(this.mEnergyUws[i]).append(" (").append(this.mTimestampsMs[i]).append(')');
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
