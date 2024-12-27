package com.android.server.power.stats;

import android.os.BatteryStats;
import android.util.Log;

import com.android.internal.os.PowerStats;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public abstract class PowerStatsProcessor {

    public final class CombinedDeviceStateEstimate {
        public final List deviceStateEstimations = new ArrayList();
        public final String id;
        public Object intermediates;

        public CombinedDeviceStateEstimate(MultiStateStats.States[] statesArr, int[] iArr) {
            this.id = PowerStatsProcessor.m847$$Nest$smconcatLabels(statesArr, iArr);
        }
    }

    public final class DeviceStateEstimation {
        public final String id;
        public Object intermediates;
        public final int[] stateValues;

        public DeviceStateEstimation(MultiStateStats.States[] statesArr, int[] iArr) {
            this.id = PowerStatsProcessor.m847$$Nest$smconcatLabels(statesArr, iArr);
            this.stateValues = iArr;
        }
    }

    public final class PowerEstimationPlan {
        public final AggregatedPowerStatsConfig.PowerComponent mConfig;
        public final List deviceStateEstimations = new ArrayList();
        public final List combinedDeviceStateEstimations = new ArrayList();
        public final List uidStateEstimates = new ArrayList();

        public PowerEstimationPlan(AggregatedPowerStatsConfig.PowerComponent powerComponent) {
            UidStateEstimate uidStateEstimate;
            int findTrackedStateByName;
            this.mConfig = powerComponent;
            MultiStateStats.States[] deviceStateConfig = powerComponent.getDeviceStateConfig();
            for (int[] iArr :
                    PowerStatsProcessor.m848$$Nest$smgetAllTrackedStateCombinations(
                            deviceStateConfig)) {
                this.deviceStateEstimations.add(new DeviceStateEstimation(deviceStateConfig, iArr));
            }
            AggregatedPowerStatsConfig.PowerComponent powerComponent2 = this.mConfig;
            MultiStateStats.States[] deviceStateConfig2 = powerComponent2.getDeviceStateConfig();
            MultiStateStats.States[] uidStateConfig = powerComponent2.getUidStateConfig();
            MultiStateStats.States[] statesArr = new MultiStateStats.States[2];
            for (int i = 0; i < 2; i++) {
                MultiStateStats.States states = deviceStateConfig2[i];
                if (states.mTracked
                        && (findTrackedStateByName =
                                        MultiStateStats.States.findTrackedStateByName(
                                                uidStateConfig, states.mName))
                                != -1
                        && uidStateConfig[findTrackedStateByName].mTracked) {
                    statesArr[i] = deviceStateConfig2[i];
                }
            }
            combineDeviceStateEstimationsRecursively(deviceStateConfig2, statesArr, new int[2], 0);
            AggregatedPowerStatsConfig.PowerComponent powerComponent3 = this.mConfig;
            MultiStateStats.States[] deviceStateConfig3 = powerComponent3.getDeviceStateConfig();
            MultiStateStats.States[] uidStateConfig2 = powerComponent3.getUidStateConfig();
            MultiStateStats.States[] statesArr2 = new MultiStateStats.States[3];
            MultiStateStats.States[] statesArr3 = new MultiStateStats.States[3];
            for (int i2 = 0; i2 < 3; i2++) {
                MultiStateStats.States states2 = uidStateConfig2[i2];
                if (states2.mTracked) {
                    int findTrackedStateByName2 =
                            MultiStateStats.States.findTrackedStateByName(
                                    deviceStateConfig3, states2.mName);
                    if (findTrackedStateByName2 == -1
                            || !deviceStateConfig3[findTrackedStateByName2].mTracked) {
                        statesArr3[i2] = uidStateConfig2[i2];
                    } else {
                        statesArr2[i2] = uidStateConfig2[i2];
                    }
                }
            }
            for (int[] iArr2 :
                    PowerStatsProcessor.m848$$Nest$smgetAllTrackedStateCombinations(
                            uidStateConfig2)) {
                CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                        getCombinedDeviceStateEstimate(statesArr2, iArr2);
                if (combinedDeviceStateEstimate == null) {
                    Log.wtf(
                            "PowerStatsProcessor",
                            "Mismatch in UID and combined device states: "
                                    + PowerStatsProcessor.m847$$Nest$smconcatLabels(
                                            statesArr2, iArr2));
                } else {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.uidStateEstimates.size()) {
                            uidStateEstimate = null;
                            break;
                        }
                        uidStateEstimate = (UidStateEstimate) this.uidStateEstimates.get(i3);
                        if (uidStateEstimate.combinedDeviceStateEstimate
                                == combinedDeviceStateEstimate) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (uidStateEstimate == null) {
                        uidStateEstimate =
                                new UidStateEstimate(combinedDeviceStateEstimate, statesArr3);
                        this.uidStateEstimates.add(uidStateEstimate);
                    }
                    ((ArrayList) uidStateEstimate.proportionalEstimates)
                            .add(new UidStateProportionalEstimate(iArr2));
                }
            }
        }

        public final void combineDeviceStateEstimationsRecursively(
                MultiStateStats.States[] statesArr,
                MultiStateStats.States[] statesArr2,
                int[] iArr,
                int i) {
            DeviceStateEstimation deviceStateEstimation;
            int i2 = 0;
            if (i < statesArr.length) {
                if (!statesArr[i].mTracked) {
                    combineDeviceStateEstimationsRecursively(statesArr, statesArr2, iArr, i + 1);
                    return;
                }
                while (i2 < statesArr[i].mLabels.length) {
                    iArr[i] = i2;
                    combineDeviceStateEstimationsRecursively(statesArr, statesArr2, iArr, i + 1);
                    i2++;
                }
                return;
            }
            String m847$$Nest$smconcatLabels =
                    PowerStatsProcessor.m847$$Nest$smconcatLabels(
                            this.mConfig.getDeviceStateConfig(), iArr);
            while (true) {
                if (i2 >= ((ArrayList) this.deviceStateEstimations).size()) {
                    deviceStateEstimation = null;
                    break;
                }
                deviceStateEstimation =
                        (DeviceStateEstimation) ((ArrayList) this.deviceStateEstimations).get(i2);
                if (deviceStateEstimation.id.equals(m847$$Nest$smconcatLabels)) {
                    break;
                } else {
                    i2++;
                }
            }
            CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                    getCombinedDeviceStateEstimate(statesArr2, iArr);
            if (combinedDeviceStateEstimate == null) {
                combinedDeviceStateEstimate = new CombinedDeviceStateEstimate(statesArr2, iArr);
                ((ArrayList) this.combinedDeviceStateEstimations).add(combinedDeviceStateEstimate);
            }
            ((ArrayList) combinedDeviceStateEstimate.deviceStateEstimations)
                    .add(deviceStateEstimation);
        }

        public final CombinedDeviceStateEstimate getCombinedDeviceStateEstimate(
                MultiStateStats.States[] statesArr, int[] iArr) {
            String m847$$Nest$smconcatLabels =
                    PowerStatsProcessor.m847$$Nest$smconcatLabels(statesArr, iArr);
            for (int i = 0; i < ((ArrayList) this.combinedDeviceStateEstimations).size(); i++) {
                CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                        (CombinedDeviceStateEstimate)
                                ((ArrayList) this.combinedDeviceStateEstimations).get(i);
                if (combinedDeviceStateEstimate.id.equals(m847$$Nest$smconcatLabels)) {
                    return combinedDeviceStateEstimate;
                }
            }
            return null;
        }

        public final void resetIntermediates() {
            int size = ((ArrayList) this.deviceStateEstimations).size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((DeviceStateEstimation) ((ArrayList) this.deviceStateEstimations).get(size))
                                    .intermediates =
                            null;
                }
            }
            for (int size2 = ((ArrayList) this.deviceStateEstimations).size() - 1;
                    size2 >= 0;
                    size2--) {
                ((DeviceStateEstimation) ((ArrayList) this.deviceStateEstimations).get(size2))
                                .intermediates =
                        null;
            }
            for (int size3 = ((ArrayList) this.uidStateEstimates).size() - 1; size3 >= 0; size3--) {
                ArrayList arrayList =
                        (ArrayList)
                                ((UidStateEstimate) ((ArrayList) this.uidStateEstimates).get(size3))
                                        .proportionalEstimates;
                for (int size4 = arrayList.size() - 1; size4 >= 0; size4--) {
                    ((UidStateProportionalEstimate) arrayList.get(size4)).getClass();
                }
            }
        }

        public final String toString() {
            StringBuilder sb =
                    new StringBuilder(
                            "Step 1. Compute device-wide power estimates for state"
                                + " combinations:\n");
            Iterator it = ((ArrayList) this.deviceStateEstimations).iterator();
            while (it.hasNext()) {
                DeviceStateEstimation deviceStateEstimation = (DeviceStateEstimation) it.next();
                sb.append("    ");
                sb.append(deviceStateEstimation.id);
                sb.append("\n");
            }
            sb.append("Step 2. Combine device-wide estimates that are untracked per UID:\n");
            Iterator it2 = ((ArrayList) this.combinedDeviceStateEstimations).iterator();
            boolean z = false;
            while (it2.hasNext()) {
                CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                        (CombinedDeviceStateEstimate) it2.next();
                if (((ArrayList) combinedDeviceStateEstimate.deviceStateEstimations).size() > 1) {
                    sb.append("    ");
                    sb.append(combinedDeviceStateEstimate.id);
                    sb.append(": ");
                    for (int i = 0;
                            i
                                    < ((ArrayList)
                                                    combinedDeviceStateEstimate
                                                            .deviceStateEstimations)
                                            .size();
                            i++) {
                        if (i != 0) {
                            sb.append(" + ");
                        }
                        sb.append(
                                ((DeviceStateEstimation)
                                                ((ArrayList)
                                                                combinedDeviceStateEstimate
                                                                        .deviceStateEstimations)
                                                        .get(i))
                                        .id);
                    }
                    sb.append("\n");
                    z = true;
                }
            }
            if (!z) {
                sb.append("    N/A\n");
            }
            sb.append("Step 3. Proportionally distribute power estimates to UIDs:\n");
            Iterator it3 = ((ArrayList) this.uidStateEstimates).iterator();
            while (it3.hasNext()) {
                UidStateEstimate uidStateEstimate = (UidStateEstimate) it3.next();
                sb.append("    ");
                sb.append(uidStateEstimate.combinedDeviceStateEstimate.id);
                sb.append("\n        among: ");
                for (int i2 = 0;
                        i2 < ((ArrayList) uidStateEstimate.proportionalEstimates).size();
                        i2++) {
                    UidStateProportionalEstimate uidStateProportionalEstimate =
                            (UidStateProportionalEstimate)
                                    ((ArrayList) uidStateEstimate.proportionalEstimates).get(i2);
                    if (i2 != 0) {
                        sb.append(", ");
                    }
                    sb.append(
                            PowerStatsProcessor.m847$$Nest$smconcatLabels(
                                    uidStateEstimate.states,
                                    uidStateProportionalEstimate.stateValues));
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public final class UidStateEstimate {
        public final CombinedDeviceStateEstimate combinedDeviceStateEstimate;
        public final List proportionalEstimates = new ArrayList();
        public final MultiStateStats.States[] states;

        public UidStateEstimate(
                CombinedDeviceStateEstimate combinedDeviceStateEstimate,
                MultiStateStats.States[] statesArr) {
            this.combinedDeviceStateEstimate = combinedDeviceStateEstimate;
            this.states = statesArr;
        }
    }

    public final class UidStateProportionalEstimate {
        public final int[] stateValues;

        public UidStateProportionalEstimate(int[] iArr) {
            this.stateValues = iArr;
        }
    }

    /* renamed from: -$$Nest$smconcatLabels, reason: not valid java name */
    public static String m847$$Nest$smconcatLabels(MultiStateStats.States[] statesArr, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < statesArr.length; i++) {
            MultiStateStats.States states = statesArr[i];
            if (states != null && states.mTracked) {
                arrayList.add(statesArr[i].mName + "=" + statesArr[i].mLabels[iArr[i]]);
            }
        }
        Collections.sort(arrayList);
        return arrayList.toString();
    }

    /* renamed from: -$$Nest$smgetAllTrackedStateCombinations, reason: not valid java name */
    public static int[][] m848$$Nest$smgetAllTrackedStateCombinations(
            MultiStateStats.States[] statesArr) {
        final ArrayList arrayList = new ArrayList();
        MultiStateStats.States.forEachTrackedStateCombination(
                new Consumer() { // from class:
                                 // com.android.server.power.stats.PowerStatsProcessor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int[] iArr = (int[]) obj;
                        arrayList.add(Arrays.copyOf(iArr, iArr.length));
                    }
                },
                statesArr,
                new int[statesArr.length],
                0);
        return (int[][])
                arrayList.toArray(
                        (int[][]) Array.newInstance((Class<?>) Integer.TYPE, arrayList.size(), 0));
    }

    public void addPowerStats(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            PowerStats powerStats,
            long j) {
        powerComponentAggregatedPowerStats.addProcessedPowerStats(powerStats, j);
    }

    public abstract void finish(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j);

    public void noteStateChange(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            BatteryStats.HistoryItem historyItem) {}

    public void start(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {}
}
