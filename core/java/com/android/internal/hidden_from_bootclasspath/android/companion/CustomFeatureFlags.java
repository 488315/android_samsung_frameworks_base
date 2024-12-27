package com.android.internal.hidden_from_bootclasspath.android.companion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ASSOCIATION_TAG,
                            Flags.FLAG_COMPANION_TRANSPORT_APIS,
                            Flags.FLAG_DEVICE_PRESENCE,
                            Flags.FLAG_NEW_ASSOCIATION_BUILDER,
                            Flags.FLAG_ONGOING_PERM_SYNC,
                            Flags.FLAG_PERM_SYNC_USER_CONSENT,
                            Flags.FLAG_UNPAIR_ASSOCIATED_DEVICE,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean associationTag() {
        return getValue(
                Flags.FLAG_ASSOCIATION_TAG,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).associationTag();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean companionTransportApis() {
        return getValue(
                Flags.FLAG_COMPANION_TRANSPORT_APIS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).companionTransportApis();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean devicePresence() {
        return getValue(
                Flags.FLAG_DEVICE_PRESENCE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).devicePresence();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean newAssociationBuilder() {
        return getValue(
                Flags.FLAG_NEW_ASSOCIATION_BUILDER,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).newAssociationBuilder();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean ongoingPermSync() {
        return getValue(
                Flags.FLAG_ONGOING_PERM_SYNC,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ongoingPermSync();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean permSyncUserConsent() {
        return getValue(
                Flags.FLAG_PERM_SYNC_USER_CONSENT,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).permSyncUserConsent();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.companion.FeatureFlags
    public boolean unpairAssociatedDevice() {
        return getValue(
                Flags.FLAG_UNPAIR_ASSOCIATED_DEVICE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.companion.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).unpairAssociatedDevice();
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
                Flags.FLAG_ASSOCIATION_TAG,
                Flags.FLAG_COMPANION_TRANSPORT_APIS,
                Flags.FLAG_DEVICE_PRESENCE,
                Flags.FLAG_NEW_ASSOCIATION_BUILDER,
                Flags.FLAG_ONGOING_PERM_SYNC,
                Flags.FLAG_PERM_SYNC_USER_CONSENT,
                Flags.FLAG_UNPAIR_ASSOCIATED_DEVICE);
    }
}
