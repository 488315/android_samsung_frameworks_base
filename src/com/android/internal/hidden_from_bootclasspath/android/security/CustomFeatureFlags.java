package com.android.internal.hidden_from_bootclasspath.android.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_AAPM_API, Flags.FLAG_AAPM_FEATURE_DISABLE_CELLULAR_2G, Flags.FLAG_AAPM_FEATURE_DISABLE_INSTALL_UNKNOWN_SOURCES, Flags.FLAG_AAPM_FEATURE_MEMORY_TAGGING_EXTENSION, Flags.FLAG_AAPM_FEATURE_USB_DATA_PROTECTION, Flags.FLAG_AFL_API, Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT, Flags.FLAG_ASM_REINTRODUCE_GRACE_PERIOD, Flags.FLAG_ASM_RESTRICTIONS_ENABLED, Flags.FLAG_ASM_TOASTS_ENABLED, Flags.FLAG_BLOCK_NULL_ACTION_INTENTS, Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION, Flags.FLAG_CLEAR_STRONG_AUTH_ON_ADDING_PRIMARY_CREDENTIAL, Flags.FLAG_CONTENT_URI_PERMISSION_APIS, Flags.FLAG_DISABLE_ADAPTIVE_AUTH_COUNTER_LOCK, Flags.FLAG_ENABLE_INTENT_MATCHING_FLAGS, Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH, Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS, Flags.FLAG_FRP_ENFORCEMENT, Flags.FLAG_FSVERITY_API, Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED, Flags.FLAG_KEYSTORE_GRANT_API, Flags.FLAG_MGF1_DIGEST_SETTER_V2, Flags.FLAG_PREVENT_INTENT_REDIRECT, Flags.FLAG_PREVENT_INTENT_REDIRECT_ABORT_OR_THROW_EXCEPTION, Flags.FLAG_PREVENT_INTENT_REDIRECT_COLLECT_NESTED_KEYS_ON_SERVER_IF_NOT_COLLECTED, Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST, Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST_IF_NESTED_KEYS_NOT_COLLECTED_R_W, Flags.FLAG_PREVENT_INTENT_REDIRECT_THROW_EXCEPTION_IF_NESTED_KEYS_NOT_COLLECTED, Flags.FLAG_PROTECT_DEVICE_CONFIG_FLAGS, Flags.FLAG_SECURE_ARRAY_ZEROIZATION, Flags.FLAG_SECURE_LOCKDOWN, Flags.FLAG_SHOULD_TRUST_MANAGER_LISTEN_FOR_PRIMARY_AUTH, Flags.FLAG_SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE_PERM_PRIV_FLAG, Flags.FLAG_UNLOCKED_STORAGE_API, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmApi() {
        return getValue(Flags.FLAG_AAPM_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aapmApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureDisableCellular2g() {
        return getValue(Flags.FLAG_AAPM_FEATURE_DISABLE_CELLULAR_2G, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aapmFeatureDisableCellular2g();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureDisableInstallUnknownSources() {
        return getValue(Flags.FLAG_AAPM_FEATURE_DISABLE_INSTALL_UNKNOWN_SOURCES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aapmFeatureDisableInstallUnknownSources();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureMemoryTaggingExtension() {
        return getValue(Flags.FLAG_AAPM_FEATURE_MEMORY_TAGGING_EXTENSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aapmFeatureMemoryTaggingExtension();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aapmFeatureUsbDataProtection() {
        return getValue(Flags.FLAG_AAPM_FEATURE_USB_DATA_PROTECTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aapmFeatureUsbDataProtection();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean aflApi() {
        return getValue(Flags.FLAG_AFL_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aflApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmOptSystemIntoEnforcement() {
        return getValue(Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).asmOptSystemIntoEnforcement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmReintroduceGracePeriod() {
        return getValue(Flags.FLAG_ASM_REINTRODUCE_GRACE_PERIOD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).asmReintroduceGracePeriod();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return getValue(Flags.FLAG_ASM_RESTRICTIONS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).asmRestrictionsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return getValue(Flags.FLAG_ASM_TOASTS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).asmToastsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return getValue(Flags.FLAG_BLOCK_NULL_ACTION_INTENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).blockNullActionIntents();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return getValue(Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).certificateTransparencyConfiguration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean clearStrongAuthOnAddingPrimaryCredential() {
        return getValue(Flags.FLAG_CLEAR_STRONG_AUTH_ON_ADDING_PRIMARY_CREDENTIAL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearStrongAuthOnAddingPrimaryCredential();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return getValue(Flags.FLAG_CONTENT_URI_PERMISSION_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).contentUriPermissionApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean disableAdaptiveAuthCounterLock() {
        return getValue(Flags.FLAG_DISABLE_ADAPTIVE_AUTH_COUNTER_LOCK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableAdaptiveAuthCounterLock();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean enableIntentMatchingFlags() {
        return getValue(Flags.FLAG_ENABLE_INTENT_MATCHING_FLAGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIntentMatchingFlags();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return getValue(Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceIntentFilterMatch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return getValue(Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extendEcmToAllSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean frpEnforcement() {
        return getValue(Flags.FLAG_FRP_ENFORCEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).frpEnforcement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean fsverityApi() {
        return getValue(Flags.FLAG_FSVERITY_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fsverityApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean internalLogEventListener() {
        return getValue(Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).internalLogEventListener();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return getValue(Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyinfoUnlockedDeviceRequired();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean keystoreGrantApi() {
        return getValue(Flags.FLAG_KEYSTORE_GRANT_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keystoreGrantApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return getValue(Flags.FLAG_MGF1_DIGEST_SETTER_V2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mgf1DigestSetterV2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirect() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirect();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectAbortOrThrowException() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT_ABORT_OR_THROW_EXCEPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirectAbortOrThrowException();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectCollectNestedKeysOnServerIfNotCollected() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT_COLLECT_NESTED_KEYS_ON_SERVER_IF_NOT_COLLECTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirectCollectNestedKeysOnServerIfNotCollected();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectShowToast() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirectShowToast();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectShowToastIfNestedKeysNotCollectedRW() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST_IF_NESTED_KEYS_NOT_COLLECTED_R_W, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirectShowToastIfNestedKeysNotCollectedRW();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean preventIntentRedirectThrowExceptionIfNestedKeysNotCollected() {
        return getValue(Flags.FLAG_PREVENT_INTENT_REDIRECT_THROW_EXCEPTION_IF_NESTED_KEYS_NOT_COLLECTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventIntentRedirectThrowExceptionIfNestedKeysNotCollected();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean protectDeviceConfigFlags() {
        return getValue(Flags.FLAG_PROTECT_DEVICE_CONFIG_FLAGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).protectDeviceConfigFlags();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean secureArrayZeroization() {
        return getValue(Flags.FLAG_SECURE_ARRAY_ZEROIZATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).secureArrayZeroization();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean secureLockdown() {
        return getValue(Flags.FLAG_SECURE_LOCKDOWN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).secureLockdown();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean shouldTrustManagerListenForPrimaryAuth() {
        return getValue(Flags.FLAG_SHOULD_TRUST_MANAGER_LISTEN_FOR_PRIMARY_AUTH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).shouldTrustManagerListenForPrimaryAuth();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean subscribeToKeyguardLockedStatePermPrivFlag() {
        return getValue(Flags.FLAG_SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE_PERM_PRIV_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).subscribeToKeyguardLockedStatePermPrivFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.security.FeatureFlags
    public boolean unlockedStorageApi() {
        return getValue(Flags.FLAG_UNLOCKED_STORAGE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.security.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unlockedStorageApi();
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
        return Arrays.asList(Flags.FLAG_AAPM_API, Flags.FLAG_AAPM_FEATURE_DISABLE_CELLULAR_2G, Flags.FLAG_AAPM_FEATURE_DISABLE_INSTALL_UNKNOWN_SOURCES, Flags.FLAG_AAPM_FEATURE_MEMORY_TAGGING_EXTENSION, Flags.FLAG_AAPM_FEATURE_USB_DATA_PROTECTION, Flags.FLAG_AFL_API, Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT, Flags.FLAG_ASM_REINTRODUCE_GRACE_PERIOD, Flags.FLAG_ASM_RESTRICTIONS_ENABLED, Flags.FLAG_ASM_TOASTS_ENABLED, Flags.FLAG_BLOCK_NULL_ACTION_INTENTS, Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION, Flags.FLAG_CLEAR_STRONG_AUTH_ON_ADDING_PRIMARY_CREDENTIAL, Flags.FLAG_CONTENT_URI_PERMISSION_APIS, Flags.FLAG_DISABLE_ADAPTIVE_AUTH_COUNTER_LOCK, Flags.FLAG_ENABLE_INTENT_MATCHING_FLAGS, Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH, Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS, Flags.FLAG_FRP_ENFORCEMENT, Flags.FLAG_FSVERITY_API, Flags.FLAG_INTERNAL_LOG_EVENT_LISTENER, Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED, Flags.FLAG_KEYSTORE_GRANT_API, Flags.FLAG_MGF1_DIGEST_SETTER_V2, Flags.FLAG_PREVENT_INTENT_REDIRECT, Flags.FLAG_PREVENT_INTENT_REDIRECT_ABORT_OR_THROW_EXCEPTION, Flags.FLAG_PREVENT_INTENT_REDIRECT_COLLECT_NESTED_KEYS_ON_SERVER_IF_NOT_COLLECTED, Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST, Flags.FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST_IF_NESTED_KEYS_NOT_COLLECTED_R_W, Flags.FLAG_PREVENT_INTENT_REDIRECT_THROW_EXCEPTION_IF_NESTED_KEYS_NOT_COLLECTED, Flags.FLAG_PROTECT_DEVICE_CONFIG_FLAGS, Flags.FLAG_SECURE_ARRAY_ZEROIZATION, Flags.FLAG_SECURE_LOCKDOWN, Flags.FLAG_SHOULD_TRUST_MANAGER_LISTEN_FOR_PRIMARY_AUTH, Flags.FLAG_SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE_PERM_PRIV_FLAG, Flags.FLAG_UNLOCKED_STORAGE_API);
    }
}
