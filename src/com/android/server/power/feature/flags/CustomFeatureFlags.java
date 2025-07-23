package com.android.server.power.feature.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DISABLE_FROZEN_PROCESS_WAKELOCKS, Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR, Flags.FLAG_ENABLE_SCREEN_TIMEOUT_POLICY_LISTENER_API, Flags.FLAG_FRAMEWORK_WAKELOCK_INFO, Flags.FLAG_IMPROVE_WAKELOCK_LATENCY, Flags.FLAG_MOVE_WSC_LOGGING_TO_NOTIFIER, Flags.FLAG_PER_DISPLAY_WAKE_BY_TOUCH, Flags.FLAG_POLICY_REASON_IN_DISPLAY_POWER_REQUEST, Flags.FLAG_WAKELOCK_ATTRIBUTION_VIA_WORKCHAIN, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean disableFrozenProcessWakelocks() {
        return getValue(Flags.FLAG_DISABLE_FROZEN_PROCESS_WAKELOCKS, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableFrozenProcessWakelocks();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableEarlyScreenTimeoutDetector() {
        return getValue(Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableEarlyScreenTimeoutDetector();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableScreenTimeoutPolicyListenerApi() {
        return getValue(Flags.FLAG_ENABLE_SCREEN_TIMEOUT_POLICY_LISTENER_API, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableScreenTimeoutPolicyListenerApi();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean frameworkWakelockInfo() {
        return getValue(Flags.FLAG_FRAMEWORK_WAKELOCK_INFO, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).frameworkWakelockInfo();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean improveWakelockLatency() {
        return getValue(Flags.FLAG_IMPROVE_WAKELOCK_LATENCY, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).improveWakelockLatency();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean moveWscLoggingToNotifier() {
        return getValue(Flags.FLAG_MOVE_WSC_LOGGING_TO_NOTIFIER, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).moveWscLoggingToNotifier();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean perDisplayWakeByTouch() {
        return getValue(Flags.FLAG_PER_DISPLAY_WAKE_BY_TOUCH, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).perDisplayWakeByTouch();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean policyReasonInDisplayPowerRequest() {
        return getValue(Flags.FLAG_POLICY_REASON_IN_DISPLAY_POWER_REQUEST, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).policyReasonInDisplayPowerRequest();
            }
        });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean wakelockAttributionViaWorkchain() {
        return getValue(Flags.FLAG_WAKELOCK_ATTRIBUTION_VIA_WORKCHAIN, new Predicate() { // from class: com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wakelockAttributionViaWorkchain();
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
        return Arrays.asList(Flags.FLAG_DISABLE_FROZEN_PROCESS_WAKELOCKS, Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR, Flags.FLAG_ENABLE_SCREEN_TIMEOUT_POLICY_LISTENER_API, Flags.FLAG_FRAMEWORK_WAKELOCK_INFO, Flags.FLAG_IMPROVE_WAKELOCK_LATENCY, Flags.FLAG_MOVE_WSC_LOGGING_TO_NOTIFIER, Flags.FLAG_PER_DISPLAY_WAKE_BY_TOUCH, Flags.FLAG_POLICY_REASON_IN_DISPLAY_POWER_REQUEST, Flags.FLAG_WAKELOCK_ATTRIBUTION_VIA_WORKCHAIN);
    }
}
