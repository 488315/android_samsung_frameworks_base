package com.android.server.flags;

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
                            Flags.FLAG_DISABLE_SYSTEM_COMPACTION,
                            Flags.FLAG_ENABLE_ODP_FEATURE_GUARD,
                            Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT,
                            Flags.FLAG_PIN_WEBVIEW,
                            Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY,
                            Flags.FLAG_SKIP_HOME_ART_PINS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean disableSystemCompaction() {
        return getValue(
                Flags.FLAG_DISABLE_SYSTEM_COMPACTION,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableSystemCompaction();
                    }
                });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean enableOdpFeatureGuard() {
        return getValue(
                Flags.FLAG_ENABLE_ODP_FEATURE_GUARD,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableOdpFeatureGuard();
                    }
                });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean newBugreportKeyboardShortcut() {
        return getValue(
                Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).newBugreportKeyboardShortcut();
                    }
                });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinWebview() {
        return getValue(
                Flags.FLAG_PIN_WEBVIEW,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).pinWebview();
                    }
                });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pkgTargetedBatteryChangedNotSticky() {
        return getValue(
                Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).pkgTargetedBatteryChangedNotSticky();
                    }
                });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean skipHomeArtPins() {
        return getValue(
                Flags.FLAG_SKIP_HOME_ART_PINS,
                new Predicate() { // from class:
                                  // com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).skipHomeArtPins();
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
                Flags.FLAG_DISABLE_SYSTEM_COMPACTION,
                Flags.FLAG_ENABLE_ODP_FEATURE_GUARD,
                Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT,
                Flags.FLAG_PIN_WEBVIEW,
                Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY,
                Flags.FLAG_SKIP_HOME_ART_PINS);
    }
}
