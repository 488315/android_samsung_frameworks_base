package com.android.internal.hidden_from_bootclasspath.android.security;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean aapmApi();

    boolean aapmFeatureDisableCellular2g();

    boolean aapmFeatureDisableInstallUnknownSources();

    boolean aapmFeatureMemoryTaggingExtension();

    boolean aapmFeatureUsbDataProtection();

    boolean aflApi();

    boolean asmOptSystemIntoEnforcement();

    boolean asmReintroduceGracePeriod();

    boolean asmRestrictionsEnabled();

    boolean asmToastsEnabled();

    boolean blockNullActionIntents();

    boolean certificateTransparencyConfiguration();

    boolean clearStrongAuthOnAddingPrimaryCredential();

    boolean contentUriPermissionApis();

    boolean disableAdaptiveAuthCounterLock();

    boolean enableIntentMatchingFlags();

    boolean enforceIntentFilterMatch();

    boolean extendEcmToAllSettings();

    boolean frpEnforcement();

    boolean fsverityApi();

    boolean internalLogEventListener();

    boolean keyinfoUnlockedDeviceRequired();

    boolean keystoreGrantApi();

    boolean mgf1DigestSetterV2();

    boolean preventIntentRedirect();

    boolean preventIntentRedirectAbortOrThrowException();

    boolean preventIntentRedirectCollectNestedKeysOnServerIfNotCollected();

    boolean preventIntentRedirectShowToast();

    boolean preventIntentRedirectShowToastIfNestedKeysNotCollectedRW();

    boolean preventIntentRedirectThrowExceptionIfNestedKeysNotCollected();

    boolean protectDeviceConfigFlags();

    boolean secureArrayZeroization();

    boolean secureLockdown();

    boolean shouldTrustManagerListenForPrimaryAuth();

    boolean subscribeToKeyguardLockedStatePermPrivFlag();

    boolean unlockedStorageApi();
}
