package com.android.server.power.stats;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public final class PowerComponentAggregatedPowerStats {
    public final AggregatedPowerStats mAggregatedPowerStats;
    public final AggregatedPowerStatsConfig.PowerComponent mConfig;
    public final MultiStateStats.States[] mDeviceStateConfig;
    public MultiStateStats mDeviceStats;
    public PowerStats.Descriptor mPowerStatsDescriptor;
    public PowerStatsProcessor mProcessor;
    public MultiStateStats.Factory mStateStatsFactory;
    public MultiStateStats.Factory mStatsFactory;
    public final MultiStateStats.States[] mUidStateConfig;
    public MultiStateStats.Factory mUidStatsFactory;
    public long[] mZeroArray;
    public final int powerComponentId;
    public final SparseArray mStateStats = new SparseArray();
    public final SparseArray mUidStats = new SparseArray();
    public final int[] mDeviceStates = new int[2];
    public long mPowerStatsTimestamp = -1;

    public final class UidStats {
        public int[] states;
        public MultiStateStats stats;
        public boolean updated;
    }

    public PowerComponentAggregatedPowerStats(
            AggregatedPowerStats aggregatedPowerStats,
            AggregatedPowerStatsConfig.PowerComponent powerComponent) {
        this.mAggregatedPowerStats = aggregatedPowerStats;
        this.mConfig = powerComponent;
        this.powerComponentId = powerComponent.mPowerComponentId;
        this.mDeviceStateConfig = powerComponent.getDeviceStateConfig();
        this.mUidStateConfig = powerComponent.getUidStateConfig();
    }

    public static void dumpMultiStateStats(
            final IndentingPrintWriter indentingPrintWriter,
            final MultiStateStats multiStateStats,
            final String str,
            final String str2,
            final PowerStats.PowerStatsFormatter powerStatsFormatter) {
        final boolean[] zArr = {true};
        MultiStateStats.Factory factory = multiStateStats.mFactory;
        final long[] jArr = new long[factory.mDimensionCount];
        final MultiStateStats.States[] statesArr = factory.mStates;
        MultiStateStats.States.forEachTrackedStateCombination(
                new Consumer() { // from class:
                                 // com.android.server.power.stats.PowerComponentAggregatedPowerStats$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MultiStateStats multiStateStats2 = MultiStateStats.this;
                        long[] jArr2 = jArr;
                        boolean[] zArr2 = zArr;
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        String str3 = str;
                        MultiStateStats.States[] statesArr2 = statesArr;
                        String str4 = str2;
                        PowerStats.PowerStatsFormatter powerStatsFormatter2 = powerStatsFormatter;
                        int[] iArr = (int[]) obj;
                        multiStateStats2.mCounter.getCounts(
                                jArr2, multiStateStats2.mFactory.getSerialState(iArr));
                        for (long j : jArr2) {
                            if (j != 0) {
                                if (zArr2[0]) {
                                    indentingPrintWriter2.println(str3);
                                    indentingPrintWriter2.increaseIndent();
                                }
                                zArr2[0] = false;
                                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("(");
                                boolean z = true;
                                for (int i = 0; i < iArr.length; i++) {
                                    if (statesArr2[i].mTracked) {
                                        if (!z) {
                                            m.append(" ");
                                        }
                                        m.append(statesArr2[i].mLabels[iArr[i]]);
                                        z = false;
                                    }
                                }
                                if (str4 != null) {
                                    m.append(" ");
                                    m.append(str4);
                                }
                                m.append(") ");
                                m.append(powerStatsFormatter2.format(jArr2));
                                indentingPrintWriter2.println(m);
                                return;
                            }
                        }
                    }
                },
                statesArr,
                new int[statesArr.length],
                0);
        if (zArr[0]) {
            return;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void addProcessedPowerStats(PowerStats powerStats, long j) {
        MultiStateStats multiStateStats;
        this.mPowerStatsDescriptor = powerStats.descriptor;
        if (this.mDeviceStats == null) {
            createDeviceStats(j);
        }
        for (int size = powerStats.stateStats.size() - 1; size >= 0; size--) {
            int keyAt = powerStats.stateStats.keyAt(size);
            MultiStateStats multiStateStats2 = (MultiStateStats) this.mStateStats.get(keyAt);
            if (multiStateStats2 == null) {
                multiStateStats2 = createStateStats(keyAt);
            }
            multiStateStats2.mCounter.incrementValues(
                    (long[]) powerStats.stateStats.valueAt(size), j);
            multiStateStats2.mTracking = true;
        }
        MultiStateStats multiStateStats3 = this.mDeviceStats;
        multiStateStats3.mCounter.incrementValues(powerStats.stats, j);
        multiStateStats3.mTracking = true;
        for (int size2 = powerStats.uidStats.size() - 1; size2 >= 0; size2--) {
            UidStats uidStats = getUidStats(powerStats.uidStats.keyAt(size2));
            if (uidStats.stats == null) {
                createUidStats(uidStats, j);
            }
            MultiStateStats multiStateStats4 = uidStats.stats;
            multiStateStats4.mCounter.incrementValues(
                    (long[]) powerStats.uidStats.valueAt(size2), j);
            multiStateStats4.mTracking = true;
            uidStats.updated = true;
        }
        for (int size3 = this.mUidStats.size() - 1; size3 >= 0; size3--) {
            UidStats uidStats2 = (UidStats) this.mUidStats.valueAt(size3);
            if (!uidStats2.updated && (multiStateStats = uidStats2.stats) != null) {
                long[] jArr = this.mZeroArray;
                if (jArr == null || jArr.length != this.mPowerStatsDescriptor.uidStatsArrayLength) {
                    this.mZeroArray = new long[this.mPowerStatsDescriptor.uidStatsArrayLength];
                }
                multiStateStats.mCounter.incrementValues(this.mZeroArray, j);
                multiStateStats.mTracking = true;
            }
            uidStats2.updated = false;
        }
        this.mPowerStatsTimestamp = j;
    }

    public final void collectUids(Collection collection) {
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            if (((UidStats) this.mUidStats.valueAt(size)).stats != null) {
                collection.add(Integer.valueOf(this.mUidStats.keyAt(size)));
            }
        }
    }

    public final void createDeviceStats(long j) {
        MultiStateStats.Factory factory = this.mStatsFactory;
        MultiStateStats.States[] statesArr = this.mDeviceStateConfig;
        if (factory == null) {
            PowerStats.Descriptor descriptor = this.mPowerStatsDescriptor;
            if (descriptor == null) {
                return;
            } else {
                this.mStatsFactory =
                        new MultiStateStats.Factory(descriptor.statsArrayLength, statesArr);
            }
        }
        MultiStateStats.Factory factory2 = this.mStatsFactory;
        this.mDeviceStats = new MultiStateStats(factory2, factory2.mDimensionCount);
        long j2 = this.mPowerStatsTimestamp;
        if (j2 != -1) {
            j = j2;
        }
        if (j != -1) {
            for (int i = 0; i < statesArr.length; i++) {
                int i2 = this.mDeviceStates[i];
                this.mDeviceStats.setState(i, i2, j);
                for (int size = this.mStateStats.size() - 1; size >= 0; size--) {
                    ((MultiStateStats) this.mStateStats.valueAt(size)).setState(i, i2, j);
                }
            }
        }
    }

    public final MultiStateStats createStateStats(int i) {
        if (this.mStateStatsFactory == null) {
            PowerStats.Descriptor descriptor = this.mPowerStatsDescriptor;
            if (descriptor == null) {
                return null;
            }
            this.mStateStatsFactory =
                    new MultiStateStats.Factory(
                            descriptor.stateStatsArrayLength, this.mDeviceStateConfig);
        }
        MultiStateStats.Factory factory = this.mStateStatsFactory;
        MultiStateStats multiStateStats = new MultiStateStats(factory, factory.mDimensionCount);
        this.mStateStats.put(i, multiStateStats);
        MultiStateStats multiStateStats2 = this.mDeviceStats;
        if (multiStateStats2 != null) {
            multiStateStats.mCounter.copyStatesFrom(multiStateStats2.mCounter);
        }
        return multiStateStats;
    }

    public final void createUidStats(UidStats uidStats, long j) {
        MultiStateStats.Factory factory = this.mUidStatsFactory;
        MultiStateStats.States[] statesArr = this.mUidStateConfig;
        if (factory == null) {
            PowerStats.Descriptor descriptor = this.mPowerStatsDescriptor;
            if (descriptor == null) {
                return;
            } else {
                this.mUidStatsFactory =
                        new MultiStateStats.Factory(descriptor.uidStatsArrayLength, statesArr);
            }
        }
        MultiStateStats.Factory factory2 = this.mUidStatsFactory;
        uidStats.stats = new MultiStateStats(factory2, factory2.mDimensionCount);
        long j2 = this.mPowerStatsTimestamp;
        if (j2 != -1) {
            j = j2;
        }
        if (j != -1) {
            for (int i = 0; i < statesArr.length; i++) {
                uidStats.stats.setState(i, uidStats.states[i], j);
            }
        }
    }

    public final void dumpDevice(IndentingPrintWriter indentingPrintWriter) {
        MultiStateStats multiStateStats = this.mDeviceStats;
        if (multiStateStats != null) {
            PowerStats.Descriptor descriptor = this.mPowerStatsDescriptor;
            dumpMultiStateStats(
                    indentingPrintWriter,
                    multiStateStats,
                    descriptor.name,
                    null,
                    descriptor.getDeviceStatsFormatter());
        }
        if (this.mStateStats.size() != 0) {
            indentingPrintWriter.increaseIndent();
            String m =
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), this.mPowerStatsDescriptor.name, " states");
            PowerStats.PowerStatsFormatter stateStatsFormatter =
                    this.mPowerStatsDescriptor.getStateStatsFormatter();
            for (int i = 0; i < this.mStateStats.size(); i++) {
                dumpMultiStateStats(
                        indentingPrintWriter,
                        (MultiStateStats) this.mStateStats.valueAt(i),
                        m,
                        this.mPowerStatsDescriptor.getStateLabel(this.mStateStats.keyAt(i)),
                        stateStatsFormatter);
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final boolean getDeviceStats(int[] iArr, long[] jArr) {
        int length = iArr.length;
        MultiStateStats.States[] statesArr = this.mDeviceStateConfig;
        if (length == statesArr.length) {
            MultiStateStats multiStateStats = this.mDeviceStats;
            if (multiStateStats == null) {
                return false;
            }
            multiStateStats.mCounter.getCounts(jArr, multiStateStats.mFactory.getSerialState(iArr));
            return true;
        }
        throw new IllegalArgumentException(
                "Invalid number of tracked states: "
                        + iArr.length
                        + " expected: "
                        + statesArr.length);
    }

    public final UidStats getUidStats(int i) {
        UidStats uidStats = (UidStats) this.mUidStats.get(i);
        if (uidStats == null) {
            uidStats = new UidStats();
            MultiStateStats.States[] statesArr = this.mUidStateConfig;
            uidStats.states = new int[statesArr.length];
            for (int i2 = 0; i2 < statesArr.length; i2++) {
                MultiStateStats.States states = statesArr[i2];
                if (states.mTracked) {
                    String str = states.mName;
                    MultiStateStats.States[] statesArr2 = this.mDeviceStateConfig;
                    int findTrackedStateByName =
                            MultiStateStats.States.findTrackedStateByName(statesArr2, str);
                    if (findTrackedStateByName != -1
                            && statesArr2[findTrackedStateByName].mTracked) {
                        uidStats.states[i2] = this.mDeviceStates[findTrackedStateByName];
                    }
                }
            }
            this.mUidStats.put(i, uidStats);
        }
        return uidStats;
    }

    public final boolean getUidStats(int i, int[] iArr, long[] jArr) {
        MultiStateStats multiStateStats;
        int length = iArr.length;
        MultiStateStats.States[] statesArr = this.mUidStateConfig;
        if (length != statesArr.length) {
            throw new IllegalArgumentException(
                    "Invalid number of tracked states: "
                            + iArr.length
                            + " expected: "
                            + statesArr.length);
        }
        UidStats uidStats = (UidStats) this.mUidStats.get(i);
        if (uidStats == null || (multiStateStats = uidStats.stats) == null) {
            return false;
        }
        multiStateStats.mCounter.getCounts(jArr, multiStateStats.mFactory.getSerialState(iArr));
        return true;
    }

    public final void setDeviceStats(int[] iArr, long[] jArr) {
        if (this.mDeviceStats == null) {
            createDeviceStats(0L);
        }
        MultiStateStats multiStateStats = this.mDeviceStats;
        multiStateStats.mCounter.setValues(multiStateStats.mFactory.getSerialState(iArr), jArr);
    }

    public final void setUidStats(int i, int[] iArr, long[] jArr) {
        MultiStateStats multiStateStats = getUidStats(i).stats;
        multiStateStats.mCounter.setValues(multiStateStats.mFactory.getSerialState(iArr), jArr);
    }

    public final String toString() {
        MultiStateStats multiStateStats;
        StringWriter stringWriter = new StringWriter();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(stringWriter);
        indentingPrintWriter.increaseIndent();
        dumpDevice(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        int size = this.mUidStats.size();
        int[] iArr = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            iArr[i] = this.mUidStats.keyAt(i);
        }
        Arrays.sort(iArr);
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = iArr[i2];
            indentingPrintWriter.println(UserHandle.formatUid(i3));
            indentingPrintWriter.increaseIndent();
            UidStats uidStats = (UidStats) this.mUidStats.get(i3);
            if (uidStats != null && (multiStateStats = uidStats.stats) != null) {
                PowerStats.Descriptor descriptor = this.mPowerStatsDescriptor;
                dumpMultiStateStats(
                        indentingPrintWriter,
                        multiStateStats,
                        descriptor.name,
                        null,
                        descriptor.getUidStatsFormatter());
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.flush();
        return stringWriter.toString();
    }
}
