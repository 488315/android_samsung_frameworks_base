package com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchSelectedCardFromQsTile() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchWalletOptionOnPowerDoubleTap() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet.FeatureFlags
    public boolean launchWalletViaSysuiCallbacks() {
        return true;
    }
}
