package com.android.server.power.stats;

import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import com.android.internal.os.PowerStats;
import com.android.server.power.stats.AggregatedPowerStatsConfig;
import com.android.server.power.stats.MultiStateStats;
import com.android.server.power.stats.PowerComponentAggregatedPowerStats;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AggregatedPowerStats {
    public final List mClockUpdates = new ArrayList();
    public final AggregatedPowerStatsConfig mConfig;
    public long mDurationMs;
    public final PowerComponentAggregatedPowerStats mGenericPowerComponent;
    public final SparseArray mPowerComponentStats;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClockUpdate {
        public long currentTime;
        public long monotonicTime;
    }

    public AggregatedPowerStats(AggregatedPowerStatsConfig aggregatedPowerStatsConfig, SparseBooleanArray sparseBooleanArray) {
        this.mConfig = aggregatedPowerStatsConfig;
        ArrayList arrayList = (ArrayList) aggregatedPowerStatsConfig.mPowerComponents;
        this.mPowerComponentStats = new SparseArray(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            AggregatedPowerStatsConfig.PowerComponent powerComponent = (AggregatedPowerStatsConfig.PowerComponent) arrayList.get(i);
            if (sparseBooleanArray.get(powerComponent.mPowerComponentId, true)) {
                this.mPowerComponentStats.put(powerComponent.mPowerComponentId, new PowerComponentAggregatedPowerStats(this, powerComponent));
            }
        }
        AggregatedPowerStatsConfig.PowerComponent powerComponent2 = new AggregatedPowerStatsConfig.PowerComponent(-1);
        powerComponent2.trackDeviceStates(0, 1);
        powerComponent2.trackUidStates(0, 1, 2);
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = new PowerComponentAggregatedPowerStats(this, powerComponent2);
        powerComponentAggregatedPowerStats.mPowerStatsDescriptor = new PowerStats.Descriptor(-1, 0, (SparseArray) null, 0, 0, new PersistableBundle());
        this.mGenericPowerComponent = powerComponentAggregatedPowerStats;
        this.mPowerComponentStats.put(-1, powerComponentAggregatedPowerStats);
    }

    public final void addClockUpdate(long j, long j2) {
        ClockUpdate clockUpdate = new ClockUpdate();
        clockUpdate.monotonicTime = j;
        clockUpdate.currentTime = j2;
        if (((ArrayList) this.mClockUpdates).size() < 100) {
            ((ArrayList) this.mClockUpdates).add(clockUpdate);
            return;
        }
        Slog.i("AggregatedPowerStats", "Too many clock updates. Replacing the previous update with " + ((Object) DateFormat.format("yyyy-MM-dd-HH-mm-ss", j2)));
        List list = this.mClockUpdates;
        ((ArrayList) list).set(((ArrayList) list).size() + (-1), clockUpdate);
    }

    public final void addPowerStats(PowerStats powerStats, long j) {
        int i = powerStats.descriptor.powerComponentId;
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = (PowerComponentAggregatedPowerStats) this.mPowerComponentStats.get(i);
        if (powerComponentAggregatedPowerStats == null) {
            AggregatedPowerStatsConfig.PowerComponent createPowerComponent = this.mConfig.createPowerComponent(i);
            if (createPowerComponent == null) {
                return;
            }
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats2 = new PowerComponentAggregatedPowerStats(this, createPowerComponent);
            powerComponentAggregatedPowerStats2.mPowerStatsDescriptor = powerStats.descriptor;
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats3 = this.mGenericPowerComponent;
            int[] iArr = powerComponentAggregatedPowerStats3.mDeviceStates;
            int length = iArr.length;
            int[] iArr2 = powerComponentAggregatedPowerStats2.mDeviceStates;
            if (length == iArr2.length) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
                if (powerComponentAggregatedPowerStats3.mDeviceStats != null) {
                    powerComponentAggregatedPowerStats2.createDeviceStats(0L);
                    MultiStateStats multiStateStats = powerComponentAggregatedPowerStats2.mDeviceStats;
                    if (multiStateStats != null) {
                        multiStateStats.mCounter.copyStatesFrom(powerComponentAggregatedPowerStats3.mDeviceStats.mCounter);
                    }
                }
            } else {
                Slog.wtf("AggregatePowerStats", "State configurations have different lengths: " + powerComponentAggregatedPowerStats3.mDeviceStates.length + " vs " + iArr2.length);
            }
            for (int size = powerComponentAggregatedPowerStats3.mUidStats.size() - 1; size >= 0; size--) {
                int keyAt = powerComponentAggregatedPowerStats3.mUidStats.keyAt(size);
                PowerComponentAggregatedPowerStats.UidStats uidStats = (PowerComponentAggregatedPowerStats.UidStats) powerComponentAggregatedPowerStats3.mUidStats.valueAt(size);
                int[] iArr3 = uidStats.states;
                if (iArr3 != null) {
                    PowerComponentAggregatedPowerStats.UidStats uidStats2 = new PowerComponentAggregatedPowerStats.UidStats();
                    uidStats2.states = Arrays.copyOf(iArr3, iArr3.length);
                    if (uidStats.stats != null) {
                        powerComponentAggregatedPowerStats2.createUidStats(uidStats2, 0L);
                        MultiStateStats multiStateStats2 = uidStats2.stats;
                        if (multiStateStats2 != null) {
                            multiStateStats2.mCounter.copyStatesFrom(uidStats.stats.mCounter);
                        }
                    }
                    powerComponentAggregatedPowerStats2.mUidStats.put(keyAt, uidStats2);
                }
            }
            if (powerComponentAggregatedPowerStats2.mProcessor == null) {
                Supplier supplier = powerComponentAggregatedPowerStats2.mConfig.mProcessorSupplier;
                powerComponentAggregatedPowerStats2.mProcessor = supplier == null ? AggregatedPowerStatsConfig.NO_OP_PROCESSOR : (PowerStatsProcessor) supplier.get();
            }
            powerComponentAggregatedPowerStats2.mProcessor.start(powerComponentAggregatedPowerStats2, j);
            this.mPowerComponentStats.put(i, powerComponentAggregatedPowerStats2);
            powerComponentAggregatedPowerStats = powerComponentAggregatedPowerStats2;
        }
        powerComponentAggregatedPowerStats.mProcessor.addPowerStats(powerComponentAggregatedPowerStats, powerStats, j);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        MultiStateStats multiStateStats;
        StringBuilder sb = new StringBuilder();
        long j = 0;
        for (int i = 0; i < ((ArrayList) this.mClockUpdates).size(); i++) {
            ClockUpdate clockUpdate = (ClockUpdate) ((ArrayList) this.mClockUpdates).get(i);
            sb.setLength(0);
            if (i == 0) {
                j = clockUpdate.monotonicTime;
                sb.append("Start time: ");
                long j2 = clockUpdate.currentTime;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                simpleDateFormat.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT"));
                sb.append(simpleDateFormat.format(new Date(j2)));
                sb.append(" (");
                sb.append(j);
                sb.append(") duration: ");
                sb.append(this.mDurationMs);
                indentingPrintWriter.println(sb);
            } else {
                sb.setLength(0);
                sb.append("Clock update:  ");
                TimeUtils.formatDuration(clockUpdate.monotonicTime - j, sb, 22);
                sb.append(" ");
                long j3 = clockUpdate.currentTime;
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                simpleDateFormat2.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT"));
                sb.append(simpleDateFormat2.format(new Date(j3)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(sb);
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.println("Device");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mPowerComponentStats.size(); i2++) {
            ((PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i2)).dumpDevice(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
        HashSet hashSet = new HashSet();
        for (int i3 = 0; i3 < this.mPowerComponentStats.size(); i3++) {
            ((PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i3)).collectUids(hashSet);
        }
        Integer[] numArr = (Integer[]) hashSet.toArray(new Integer[hashSet.size()]);
        Arrays.sort(numArr);
        for (Integer num : numArr) {
            int intValue = num.intValue();
            indentingPrintWriter.println(UserHandle.formatUid(intValue));
            indentingPrintWriter.increaseIndent();
            for (int i4 = 0; i4 < this.mPowerComponentStats.size(); i4++) {
                PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = (PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i4);
                PowerComponentAggregatedPowerStats.UidStats uidStats = (PowerComponentAggregatedPowerStats.UidStats) powerComponentAggregatedPowerStats.mUidStats.get(intValue);
                if (uidStats != null && (multiStateStats = uidStats.stats) != null) {
                    PowerStats.Descriptor descriptor = powerComponentAggregatedPowerStats.mPowerStatsDescriptor;
                    PowerComponentAggregatedPowerStats.dumpMultiStateStats(indentingPrintWriter, multiStateStats, descriptor.name, null, descriptor.getUidStatsFormatter());
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final void reset() {
        ((ArrayList) this.mClockUpdates).clear();
        this.mDurationMs = 0L;
        for (int i = 0; i < this.mPowerComponentStats.size(); i++) {
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = (PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i);
            powerComponentAggregatedPowerStats.mStatsFactory = null;
            powerComponentAggregatedPowerStats.mUidStatsFactory = null;
            powerComponentAggregatedPowerStats.mDeviceStats = null;
            powerComponentAggregatedPowerStats.mStateStats.clear();
            for (int size = powerComponentAggregatedPowerStats.mUidStats.size() - 1; size >= 0; size--) {
                ((PowerComponentAggregatedPowerStats.UidStats) powerComponentAggregatedPowerStats.mUidStats.valueAt(size)).stats = null;
            }
        }
    }

    public final void setDeviceState(int i, int i2, long j) {
        for (int i3 = 0; i3 < this.mPowerComponentStats.size(); i3++) {
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = (PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i3);
            if (powerComponentAggregatedPowerStats.mDeviceStats == null) {
                powerComponentAggregatedPowerStats.createDeviceStats(j);
            }
            powerComponentAggregatedPowerStats.mDeviceStates[i] = i2;
            MultiStateStats.States[] statesArr = powerComponentAggregatedPowerStats.mDeviceStateConfig;
            if (statesArr[i].mTracked) {
                MultiStateStats multiStateStats = powerComponentAggregatedPowerStats.mDeviceStats;
                if (multiStateStats != null) {
                    multiStateStats.setState(i, i2, j);
                }
                for (int size = powerComponentAggregatedPowerStats.mStateStats.size() - 1; size >= 0; size--) {
                    ((MultiStateStats) powerComponentAggregatedPowerStats.mStateStats.valueAt(size)).setState(i, i2, j);
                }
            }
            String str = statesArr[i].mName;
            MultiStateStats.States[] statesArr2 = powerComponentAggregatedPowerStats.mUidStateConfig;
            int findTrackedStateByName = MultiStateStats.States.findTrackedStateByName(statesArr2, str);
            if (findTrackedStateByName != -1 && statesArr2[findTrackedStateByName].mTracked) {
                for (int size2 = powerComponentAggregatedPowerStats.mUidStats.size() - 1; size2 >= 0; size2--) {
                    PowerComponentAggregatedPowerStats.UidStats uidStats = (PowerComponentAggregatedPowerStats.UidStats) powerComponentAggregatedPowerStats.mUidStats.valueAt(size2);
                    if (uidStats.stats == null) {
                        powerComponentAggregatedPowerStats.createUidStats(uidStats, j);
                    }
                    uidStats.states[findTrackedStateByName] = i2;
                    MultiStateStats multiStateStats2 = uidStats.stats;
                    if (multiStateStats2 != null) {
                        multiStateStats2.setState(findTrackedStateByName, i2, j);
                    }
                }
            }
        }
    }

    public final void setUidState(int i, int i2, long j) {
        for (int i3 = 0; i3 < this.mPowerComponentStats.size(); i3++) {
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = (PowerComponentAggregatedPowerStats) this.mPowerComponentStats.valueAt(i3);
            if (powerComponentAggregatedPowerStats.mUidStateConfig[2].mTracked) {
                PowerComponentAggregatedPowerStats.UidStats uidStats = powerComponentAggregatedPowerStats.getUidStats(i);
                if (uidStats.stats == null) {
                    powerComponentAggregatedPowerStats.createUidStats(uidStats, j);
                }
                uidStats.states[2] = i2;
                MultiStateStats multiStateStats = uidStats.stats;
                if (multiStateStats != null) {
                    multiStateStats.setState(2, i2, j);
                }
            }
        }
    }

    public final String toString() {
        StringWriter stringWriter = new StringWriter();
        dump(new IndentingPrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
