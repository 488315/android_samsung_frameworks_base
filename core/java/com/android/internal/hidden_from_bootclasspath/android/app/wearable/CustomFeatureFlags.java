package com.android.internal.hidden_from_bootclasspath.android.app.wearable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API,
                            Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API,
                            Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API,
                            Flags.FLAG_ENABLE_RESTART_WSS_PROCESS,
                            Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.wearable.FeatureFlags
    public boolean enableDataRequestObserverApi() {
        return getValue(
                Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.wearable.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDataRequestObserverApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.wearable.FeatureFlags
    public boolean enableHotwordWearableSensingApi() {
        return getValue(
                Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.wearable.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableHotwordWearableSensingApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.wearable.FeatureFlags
    public boolean enableProvideWearableConnectionApi() {
        return getValue(
                Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.wearable.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableProvideWearableConnectionApi();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.wearable.FeatureFlags
    public boolean enableRestartWssProcess() {
        return getValue(
                Flags.FLAG_ENABLE_RESTART_WSS_PROCESS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.wearable.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableRestartWssProcess();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.wearable.FeatureFlags
    public boolean enableUnsupportedOperationStatusCode() {
        return getValue(
                Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.wearable.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableUnsupportedOperationStatusCode();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API,
                Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API,
                Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API,
                Flags.FLAG_ENABLE_RESTART_WSS_PROCESS,
                Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE);
    }
}
