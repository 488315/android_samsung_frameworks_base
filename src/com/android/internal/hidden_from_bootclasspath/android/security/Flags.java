package com.android.internal.hidden_from_bootclasspath.android.security;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_AAPM_API = "android.security.aapm_api";
    public static final String FLAG_AAPM_FEATURE_DISABLE_CELLULAR_2G = "android.security.aapm_feature_disable_cellular_2g";
    public static final String FLAG_AAPM_FEATURE_DISABLE_INSTALL_UNKNOWN_SOURCES = "android.security.aapm_feature_disable_install_unknown_sources";
    public static final String FLAG_AAPM_FEATURE_MEMORY_TAGGING_EXTENSION = "android.security.aapm_feature_memory_tagging_extension";
    public static final String FLAG_AAPM_FEATURE_USB_DATA_PROTECTION = "android.security.aapm_feature_usb_data_protection";
    public static final String FLAG_AFL_API = "android.security.afl_api";
    public static final String FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT = "android.security.asm_opt_system_into_enforcement";
    public static final String FLAG_ASM_REINTRODUCE_GRACE_PERIOD = "android.security.asm_reintroduce_grace_period";
    public static final String FLAG_ASM_RESTRICTIONS_ENABLED = "android.security.asm_restrictions_enabled";
    public static final String FLAG_ASM_TOASTS_ENABLED = "android.security.asm_toasts_enabled";
    public static final String FLAG_BLOCK_NULL_ACTION_INTENTS = "android.security.block_null_action_intents";
    public static final String FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION = "android.security.certificate_transparency_configuration";
    public static final String FLAG_CLEAR_STRONG_AUTH_ON_ADDING_PRIMARY_CREDENTIAL = "android.security.clear_strong_auth_on_adding_primary_credential";
    public static final String FLAG_CONTENT_URI_PERMISSION_APIS = "android.security.content_uri_permission_apis";
    public static final String FLAG_DISABLE_ADAPTIVE_AUTH_COUNTER_LOCK = "android.security.disable_adaptive_auth_counter_lock";
    public static final String FLAG_ENABLE_INTENT_MATCHING_FLAGS = "android.security.enable_intent_matching_flags";
    public static final String FLAG_ENFORCE_INTENT_FILTER_MATCH = "android.security.enforce_intent_filter_match";
    public static final String FLAG_EXTEND_ECM_TO_ALL_SETTINGS = "android.security.extend_ecm_to_all_settings";
    public static final String FLAG_FRP_ENFORCEMENT = "android.security.frp_enforcement";
    public static final String FLAG_FSVERITY_API = "android.security.fsverity_api";
    public static final String FLAG_INTERNAL_LOG_EVENT_LISTENER = "android.security.internal_log_event_listener";
    public static final String FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED = "android.security.keyinfo_unlocked_device_required";
    public static final String FLAG_KEYSTORE_GRANT_API = "android.security.keystore_grant_api";
    public static final String FLAG_MGF1_DIGEST_SETTER_V2 = "android.security.mgf1_digest_setter_v2";
    public static final String FLAG_PREVENT_INTENT_REDIRECT = "android.security.prevent_intent_redirect";
    public static final String FLAG_PREVENT_INTENT_REDIRECT_ABORT_OR_THROW_EXCEPTION = "android.security.prevent_intent_redirect_abort_or_throw_exception";
    public static final String FLAG_PREVENT_INTENT_REDIRECT_COLLECT_NESTED_KEYS_ON_SERVER_IF_NOT_COLLECTED = "android.security.prevent_intent_redirect_collect_nested_keys_on_server_if_not_collected";
    public static final String FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST = "android.security.prevent_intent_redirect_show_toast";
    public static final String FLAG_PREVENT_INTENT_REDIRECT_SHOW_TOAST_IF_NESTED_KEYS_NOT_COLLECTED_R_W = "android.security.prevent_intent_redirect_show_toast_if_nested_keys_not_collected_r_w";
    public static final String FLAG_PREVENT_INTENT_REDIRECT_THROW_EXCEPTION_IF_NESTED_KEYS_NOT_COLLECTED = "android.security.prevent_intent_redirect_throw_exception_if_nested_keys_not_collected";
    public static final String FLAG_PROTECT_DEVICE_CONFIG_FLAGS = "android.security.protect_device_config_flags";
    public static final String FLAG_SECURE_ARRAY_ZEROIZATION = "android.security.secure_array_zeroization";
    public static final String FLAG_SECURE_LOCKDOWN = "android.security.secure_lockdown";
    public static final String FLAG_SHOULD_TRUST_MANAGER_LISTEN_FOR_PRIMARY_AUTH = "android.security.should_trust_manager_listen_for_primary_auth";
    public static final String FLAG_SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE_PERM_PRIV_FLAG = "android.security.subscribe_to_keyguard_locked_state_perm_priv_flag";
    public static final String FLAG_UNLOCKED_STORAGE_API = "android.security.unlocked_storage_api";

    public static boolean aapmApi() {
        return FEATURE_FLAGS.aapmApi();
    }

    public static boolean aapmFeatureDisableCellular2g() {
        return FEATURE_FLAGS.aapmFeatureDisableCellular2g();
    }

    public static boolean aapmFeatureDisableInstallUnknownSources() {
        return FEATURE_FLAGS.aapmFeatureDisableInstallUnknownSources();
    }

    public static boolean aapmFeatureMemoryTaggingExtension() {
        return FEATURE_FLAGS.aapmFeatureMemoryTaggingExtension();
    }

    public static boolean aapmFeatureUsbDataProtection() {
        return FEATURE_FLAGS.aapmFeatureUsbDataProtection();
    }

    public static boolean aflApi() {
        return FEATURE_FLAGS.aflApi();
    }

    public static boolean asmOptSystemIntoEnforcement() {
        return FEATURE_FLAGS.asmOptSystemIntoEnforcement();
    }

    public static boolean asmReintroduceGracePeriod() {
        return FEATURE_FLAGS.asmReintroduceGracePeriod();
    }

    public static boolean asmRestrictionsEnabled() {
        return FEATURE_FLAGS.asmRestrictionsEnabled();
    }

    public static boolean asmToastsEnabled() {
        return FEATURE_FLAGS.asmToastsEnabled();
    }

    public static boolean blockNullActionIntents() {
        return FEATURE_FLAGS.blockNullActionIntents();
    }

    public static boolean certificateTransparencyConfiguration() {
        return FEATURE_FLAGS.certificateTransparencyConfiguration();
    }

    public static boolean clearStrongAuthOnAddingPrimaryCredential() {
        return FEATURE_FLAGS.clearStrongAuthOnAddingPrimaryCredential();
    }

    public static boolean contentUriPermissionApis() {
        return FEATURE_FLAGS.contentUriPermissionApis();
    }

    public static boolean disableAdaptiveAuthCounterLock() {
        return FEATURE_FLAGS.disableAdaptiveAuthCounterLock();
    }

    public static boolean enableIntentMatchingFlags() {
        return FEATURE_FLAGS.enableIntentMatchingFlags();
    }

    public static boolean enforceIntentFilterMatch() {
        return FEATURE_FLAGS.enforceIntentFilterMatch();
    }

    public static boolean extendEcmToAllSettings() {
        return FEATURE_FLAGS.extendEcmToAllSettings();
    }

    public static boolean frpEnforcement() {
        return FEATURE_FLAGS.frpEnforcement();
    }

    public static boolean fsverityApi() {
        return FEATURE_FLAGS.fsverityApi();
    }

    public static boolean internalLogEventListener() {
        return FEATURE_FLAGS.internalLogEventListener();
    }

    public static boolean keyinfoUnlockedDeviceRequired() {
        return FEATURE_FLAGS.keyinfoUnlockedDeviceRequired();
    }

    public static boolean keystoreGrantApi() {
        return FEATURE_FLAGS.keystoreGrantApi();
    }

    public static boolean mgf1DigestSetterV2() {
        return FEATURE_FLAGS.mgf1DigestSetterV2();
    }

    public static boolean preventIntentRedirect() {
        return FEATURE_FLAGS.preventIntentRedirect();
    }

    public static boolean preventIntentRedirectAbortOrThrowException() {
        return FEATURE_FLAGS.preventIntentRedirectAbortOrThrowException();
    }

    public static boolean preventIntentRedirectCollectNestedKeysOnServerIfNotCollected() {
        return FEATURE_FLAGS.preventIntentRedirectCollectNestedKeysOnServerIfNotCollected();
    }

    public static boolean preventIntentRedirectShowToast() {
        return FEATURE_FLAGS.preventIntentRedirectShowToast();
    }

    public static boolean preventIntentRedirectShowToastIfNestedKeysNotCollectedRW() {
        return FEATURE_FLAGS.preventIntentRedirectShowToastIfNestedKeysNotCollectedRW();
    }

    public static boolean preventIntentRedirectThrowExceptionIfNestedKeysNotCollected() {
        return FEATURE_FLAGS.preventIntentRedirectThrowExceptionIfNestedKeysNotCollected();
    }

    public static boolean protectDeviceConfigFlags() {
        return FEATURE_FLAGS.protectDeviceConfigFlags();
    }

    public static boolean secureArrayZeroization() {
        return FEATURE_FLAGS.secureArrayZeroization();
    }

    public static boolean secureLockdown() {
        return FEATURE_FLAGS.secureLockdown();
    }

    public static boolean shouldTrustManagerListenForPrimaryAuth() {
        return FEATURE_FLAGS.shouldTrustManagerListenForPrimaryAuth();
    }

    public static boolean subscribeToKeyguardLockedStatePermPrivFlag() {
        return FEATURE_FLAGS.subscribeToKeyguardLockedStatePermPrivFlag();
    }

    public static boolean unlockedStorageApi() {
        return FEATURE_FLAGS.unlockedStorageApi();
    }
}
