package com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_LAUNCH_SELECTED_CARD_FROM_QS_TILE, Flags.FLAG_LAUNCH_WALLET_OPTION_ON_POWER_DOUBLE_TAP, Flags.FLAG_LAUNCH_WALLET_VIA_SYSUI_CALLBACKS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchSelectedCardFromQsTile() {
        return getValue(Flags.FLAG_LAUNCH_SELECTED_CARD_FROM_QS_TILE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).launchSelectedCardFromQsTile();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchWalletOptionOnPowerDoubleTap() {
        return getValue(Flags.FLAG_LAUNCH_WALLET_OPTION_ON_POWER_DOUBLE_TAP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).launchWalletOptionOnPowerDoubleTap();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchWalletViaSysuiCallbacks() {
        return getValue(Flags.FLAG_LAUNCH_WALLET_VIA_SYSUI_CALLBACKS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).launchWalletViaSysuiCallbacks();
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
        return Arrays.asList(Flags.FLAG_LAUNCH_SELECTED_CARD_FROM_QS_TILE, Flags.FLAG_LAUNCH_WALLET_OPTION_ON_POWER_DOUBLE_TAP, Flags.FLAG_LAUNCH_WALLET_VIA_SYSUI_CALLBACKS);
    }
}
