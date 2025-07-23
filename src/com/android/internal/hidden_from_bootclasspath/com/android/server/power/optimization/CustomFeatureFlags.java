package com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACCUMULATE_BATTERY_USAGE_STATS, Flags.FLAG_ADD_BATTERY_USAGE_STATS_SLICE_ATOM, Flags.FLAG_BATTERY_STATS_SCREEN_STATE_EVENT, Flags.FLAG_BATTERY_USAGE_STATS_BY_POWER_AND_SCREEN_STATE, Flags.FLAG_DISABLE_COMPOSITE_BATTERY_USAGE_STATS_ATOMS, Flags.FLAG_DISABLE_SYSTEM_SERVICE_POWER_ATTR, Flags.FLAG_EXTENDED_BATTERY_HISTORY_COMPRESSION_ENABLED, Flags.FLAG_EXTENDED_BATTERY_HISTORY_CONTINUOUS_COLLECTION_ENABLED, Flags.FLAG_ONEWAY_BATTERY_STATS_SERVICE, Flags.FLAG_POWER_MONITOR_API, Flags.FLAG_STREAMLINED_BATTERY_STATS, Flags.FLAG_STREAMLINED_CONNECTIVITY_BATTERY_STATS, Flags.FLAG_STREAMLINED_MISC_BATTERY_STATS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean accumulateBatteryUsageStats() {
        return getValue(Flags.FLAG_ACCUMULATE_BATTERY_USAGE_STATS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).accumulateBatteryUsageStats();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean addBatteryUsageStatsSliceAtom() {
        return getValue(Flags.FLAG_ADD_BATTERY_USAGE_STATS_SLICE_ATOM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addBatteryUsageStatsSliceAtom();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean batteryStatsScreenStateEvent() {
        return getValue(Flags.FLAG_BATTERY_STATS_SCREEN_STATE_EVENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).batteryStatsScreenStateEvent();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean batteryUsageStatsByPowerAndScreenState() {
        return getValue(Flags.FLAG_BATTERY_USAGE_STATS_BY_POWER_AND_SCREEN_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).batteryUsageStatsByPowerAndScreenState();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean disableCompositeBatteryUsageStatsAtoms() {
        return getValue(Flags.FLAG_DISABLE_COMPOSITE_BATTERY_USAGE_STATS_ATOMS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableCompositeBatteryUsageStatsAtoms();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean disableSystemServicePowerAttr() {
        return getValue(Flags.FLAG_DISABLE_SYSTEM_SERVICE_POWER_ATTR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableSystemServicePowerAttr();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean extendedBatteryHistoryCompressionEnabled() {
        return getValue(Flags.FLAG_EXTENDED_BATTERY_HISTORY_COMPRESSION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extendedBatteryHistoryCompressionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean extendedBatteryHistoryContinuousCollectionEnabled() {
        return getValue(Flags.FLAG_EXTENDED_BATTERY_HISTORY_CONTINUOUS_COLLECTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extendedBatteryHistoryContinuousCollectionEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean onewayBatteryStatsService() {
        return getValue(Flags.FLAG_ONEWAY_BATTERY_STATS_SERVICE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onewayBatteryStatsService();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean powerMonitorApi() {
        return getValue(Flags.FLAG_POWER_MONITOR_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).powerMonitorApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedBatteryStats() {
        return getValue(Flags.FLAG_STREAMLINED_BATTERY_STATS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).streamlinedBatteryStats();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedConnectivityBatteryStats() {
        return getValue(Flags.FLAG_STREAMLINED_CONNECTIVITY_BATTERY_STATS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).streamlinedConnectivityBatteryStats();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.FeatureFlags
    public boolean streamlinedMiscBatteryStats() {
        return getValue(Flags.FLAG_STREAMLINED_MISC_BATTERY_STATS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).streamlinedMiscBatteryStats();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ACCUMULATE_BATTERY_USAGE_STATS, Flags.FLAG_ADD_BATTERY_USAGE_STATS_SLICE_ATOM, Flags.FLAG_BATTERY_STATS_SCREEN_STATE_EVENT, Flags.FLAG_BATTERY_USAGE_STATS_BY_POWER_AND_SCREEN_STATE, Flags.FLAG_DISABLE_COMPOSITE_BATTERY_USAGE_STATS_ATOMS, Flags.FLAG_DISABLE_SYSTEM_SERVICE_POWER_ATTR, Flags.FLAG_EXTENDED_BATTERY_HISTORY_COMPRESSION_ENABLED, Flags.FLAG_EXTENDED_BATTERY_HISTORY_CONTINUOUS_COLLECTION_ENABLED, Flags.FLAG_ONEWAY_BATTERY_STATS_SERVICE, Flags.FLAG_POWER_MONITOR_API, Flags.FLAG_STREAMLINED_BATTERY_STATS, Flags.FLAG_STREAMLINED_CONNECTIVITY_BATTERY_STATS, Flags.FLAG_STREAMLINED_MISC_BATTERY_STATS);
    }
}
