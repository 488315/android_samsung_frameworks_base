package com.android.server.power.stats;

import android.os.BatteryConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AggregatedPowerStatsConfig {
    public static final AnonymousClass1 NO_OP_PROCESSOR;
    public static final String[] STATE_LABELS_PROCESS_STATE;
    public PowerComponent mCustomPowerComponent;
    public Supplier mCustomPowerStatsProcessorFactory;
    public final List mPowerComponents = new ArrayList();
    public static final String[] STATE_LABELS_POWER = {"pwr-battery", "pwr-other"};
    public static final String[] STATE_LABELS_SCREEN = {"scr-on", "scr-other"};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.AggregatedPowerStatsConfig$1, reason: invalid class name */
    public final class AnonymousClass1 extends PowerStatsProcessor {
        @Override // com.android.server.power.stats.PowerStatsProcessor
        public final void finish(
                PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerComponent {
        public final int mPowerComponentId;
        public Supplier mProcessorSupplier;
        public int[] mTrackedDeviceStates;
        public int[] mTrackedUidStates;

        public PowerComponent(int i) {
            this.mPowerComponentId = i;
        }

        public static boolean isTracked(int i, int[] iArr) {
            if (iArr == null) {
                return false;
            }
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public final MultiStateStats.States[] getDeviceStateConfig() {
            return new MultiStateStats.States[] {
                new MultiStateStats.States(
                        "pwr",
                        isTracked(0, this.mTrackedDeviceStates),
                        AggregatedPowerStatsConfig.STATE_LABELS_POWER),
                new MultiStateStats.States(
                        "scr",
                        isTracked(1, this.mTrackedDeviceStates),
                        AggregatedPowerStatsConfig.STATE_LABELS_SCREEN)
            };
        }

        public final MultiStateStats.States[] getUidStateConfig() {
            return new MultiStateStats.States[] {
                new MultiStateStats.States(
                        "pwr",
                        isTracked(0, this.mTrackedUidStates),
                        AggregatedPowerStatsConfig.STATE_LABELS_POWER),
                new MultiStateStats.States(
                        "scr",
                        isTracked(1, this.mTrackedUidStates),
                        AggregatedPowerStatsConfig.STATE_LABELS_SCREEN),
                new MultiStateStats.States(
                        "ps",
                        isTracked(2, this.mTrackedUidStates),
                        AggregatedPowerStatsConfig.STATE_LABELS_PROCESS_STATE)
            };
        }

        public final void trackDeviceStates(int... iArr) {
            if (this.mTrackedDeviceStates != null) {
                throw new IllegalStateException("Component is already configured");
            }
            this.mTrackedDeviceStates = iArr;
        }

        public final void trackUidStates(int... iArr) {
            if (this.mTrackedUidStates != null) {
                throw new IllegalStateException("Component is already configured");
            }
            this.mTrackedUidStates = iArr;
        }
    }

    static {
        String[] strArr = new String[5];
        for (int i = 0; i < 5; i++) {
            strArr[i] = BatteryConsumer.processStateToString(i);
        }
        STATE_LABELS_PROCESS_STATE = strArr;
        NO_OP_PROCESSOR = new AnonymousClass1();
    }

    public final PowerComponent createPowerComponent(int i) {
        PowerComponent powerComponent = this.mCustomPowerComponent;
        if (powerComponent == null) {
            return null;
        }
        PowerComponent powerComponent2 = new PowerComponent(i);
        powerComponent2.trackDeviceStates(powerComponent.mTrackedDeviceStates);
        powerComponent2.trackUidStates(this.mCustomPowerComponent.mTrackedUidStates);
        Supplier supplier = this.mCustomPowerStatsProcessorFactory;
        if (supplier != null) {
            powerComponent2.mProcessorSupplier = supplier;
        }
        return powerComponent2;
    }

    public final PowerComponent trackPowerComponent(int i) {
        PowerComponent powerComponent = new PowerComponent(i);
        ((ArrayList) this.mPowerComponents).add(powerComponent);
        return powerComponent;
    }
}
