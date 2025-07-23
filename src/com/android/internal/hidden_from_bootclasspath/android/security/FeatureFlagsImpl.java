package com.android.internal.hidden_from_bootclasspath.android.security;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureDisableCellular2g() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureDisableInstallUnknownSources() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureMemoryTaggingExtension() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureUsbDataProtection() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aflApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmOptSystemIntoEnforcement() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmReintroduceGracePeriod() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean clearStrongAuthOnAddingPrimaryCredential() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean disableAdaptiveAuthCounterLock() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean enableIntentMatchingFlags() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean frpEnforcement() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean fsverityApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean internalLogEventListener() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean keystoreGrantApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirect() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectAbortOrThrowException() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectCollectNestedKeysOnServerIfNotCollected() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectShowToast() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectShowToastIfNestedKeysNotCollectedRW() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectThrowExceptionIfNestedKeysNotCollected() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean protectDeviceConfigFlags() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean secureArrayZeroization() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean secureLockdown() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean shouldTrustManagerListenForPrimaryAuth() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean subscribeToKeyguardLockedStatePermPrivFlag() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean unlockedStorageApi() {
        return false;
    }
}
