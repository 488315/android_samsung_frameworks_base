package android.os;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final class PowerMonitorReadings {
    public static final int ENERGY_UNAVAILABLE = -1;

    @SystemApi
    public static final int GRANULARITY_FINE = 1;

    @SystemApi
    public static final int GRANULARITY_UNSPECIFIED = 0;
    private static final Comparator<PowerMonitor> POWER_MONITOR_COMPARATOR = Comparator.comparingInt(new ToIntFunction() { // from class: android.os.PowerMonitorReadings$$ExternalSyntheticLambda0
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            int i;
            i = ((PowerMonitor) obj).index;
            return i;
        }
    });
    private final long[] mEnergyUws;
    private final int mGranularity;
    private final PowerMonitor[] mPowerMonitors;
    private final long[] mTimestampsMs;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerMonitorGranularity {
    }

    public PowerMonitorReadings(PowerMonitor[] powerMonitorArr, long[] jArr, long[] jArr2, int i) {
        this.mPowerMonitors = powerMonitorArr;
        this.mEnergyUws = jArr;
        this.mTimestampsMs = jArr2;
        this.mGranularity = i;
    }

    public long getConsumedEnergy(PowerMonitor powerMonitor) {
        int binarySearch = Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (binarySearch >= 0) {
            return this.mEnergyUws[binarySearch];
        }
        return -1L;
    }

    public long getTimestampMillis(PowerMonitor powerMonitor) {
        int binarySearch = Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (binarySearch >= 0) {
            return this.mTimestampsMs[binarySearch];
        }
        return 0L;
    }

    @SystemApi
    public int getGranularity() {
        return this.mGranularity;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(" monitors: [");
        for (int i = 0; i < this.mPowerMonitors.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mPowerMonitors[i].getName());
            sb.append(" = ");
            sb.append(this.mEnergyUws[i]);
            sb.append(" (");
            sb.append(this.mTimestampsMs[i]);
            sb.append(')');
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
