package android.nfc;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_CARD_EMULATION_EUICC = "android.nfc.enable_card_emulation_euicc";
    public static final String FLAG_ENABLE_NFC_CHARGING = "android.nfc.enable_nfc_charging";
    public static final String FLAG_ENABLE_NFC_MAINLINE = "android.nfc.enable_nfc_mainline";
    public static final String FLAG_ENABLE_NFC_READER_OPTION = "android.nfc.enable_nfc_reader_option";
    public static final String FLAG_ENABLE_NFC_SET_DISCOVERY_TECH = "android.nfc.enable_nfc_set_discovery_tech";
    public static final String FLAG_ENABLE_NFC_USER_RESTRICTION = "android.nfc.enable_nfc_user_restriction";
    public static final String FLAG_ENABLE_TAG_DETECTION_BROADCASTS = "android.nfc.enable_tag_detection_broadcasts";
    public static final String FLAG_NFC_ACTION_MANAGE_SERVICES_SETTINGS = "android.nfc.nfc_action_manage_services_settings";
    public static final String FLAG_NFC_APDU_SERVICE_INFO_CONSTRUCTOR = "android.nfc.nfc_apdu_service_info_constructor";
    public static final String FLAG_NFC_ASSOCIATED_ROLE_SERVICES = "android.nfc.nfc_associated_role_services";
    public static final String FLAG_NFC_CHECK_TAG_INTENT_PREFERENCE = "android.nfc.nfc_check_tag_intent_preference";
    public static final String FLAG_NFC_EVENT_LISTENER = "android.nfc.nfc_event_listener";
    public static final String FLAG_NFC_OBSERVE_MODE = "android.nfc.nfc_observe_mode";
    public static final String FLAG_NFC_OBSERVE_MODE_ST_SHIM = "android.nfc.nfc_observe_mode_st_shim";
    public static final String FLAG_NFC_OEM_EXTENSION = "android.nfc.nfc_oem_extension";
    public static final String FLAG_NFC_OVERRIDE_RECOVER_ROUTING_TABLE = "android.nfc.nfc_override_recover_routing_table";
    public static final String FLAG_NFC_PERSIST_LOG = "android.nfc.nfc_persist_log";
    public static final String FLAG_NFC_READ_POLLING_LOOP = "android.nfc.nfc_read_polling_loop";
    public static final String FLAG_NFC_READ_POLLING_LOOP_ST_SHIM = "android.nfc.nfc_read_polling_loop_st_shim";
    public static final String FLAG_NFC_SET_DEFAULT_DISC_TECH = "android.nfc.nfc_set_default_disc_tech";
    public static final String FLAG_NFC_SET_SERVICE_ENABLED_FOR_CATEGORY_OTHER = "android.nfc.nfc_set_service_enabled_for_category_other";
    public static final String FLAG_NFC_STATE_CHANGE = "android.nfc.nfc_state_change";
    public static final String FLAG_NFC_STATE_CHANGE_SECURITY_LOG_EVENT_ENABLED = "android.nfc.nfc_state_change_security_log_event_enabled";
    public static final String FLAG_NFC_VENDOR_CMD = "android.nfc.nfc_vendor_cmd";
    public static final String FLAG_NFC_WATCHDOG = "android.nfc.nfc_watchdog";

    public static boolean enableCardEmulationEuicc() {
        return FEATURE_FLAGS.enableCardEmulationEuicc();
    }

    public static boolean enableNfcCharging() {
        return FEATURE_FLAGS.enableNfcCharging();
    }

    public static boolean enableNfcMainline() {
        return FEATURE_FLAGS.enableNfcMainline();
    }

    public static boolean enableNfcReaderOption() {
        return FEATURE_FLAGS.enableNfcReaderOption();
    }

    public static boolean enableNfcSetDiscoveryTech() {
        return FEATURE_FLAGS.enableNfcSetDiscoveryTech();
    }

    public static boolean enableNfcUserRestriction() {
        return FEATURE_FLAGS.enableNfcUserRestriction();
    }

    public static boolean enableTagDetectionBroadcasts() {
        return FEATURE_FLAGS.enableTagDetectionBroadcasts();
    }

    public static boolean nfcActionManageServicesSettings() {
        return FEATURE_FLAGS.nfcActionManageServicesSettings();
    }

    public static boolean nfcApduServiceInfoConstructor() {
        return FEATURE_FLAGS.nfcApduServiceInfoConstructor();
    }

    public static boolean nfcAssociatedRoleServices() {
        return FEATURE_FLAGS.nfcAssociatedRoleServices();
    }

    public static boolean nfcCheckTagIntentPreference() {
        return FEATURE_FLAGS.nfcCheckTagIntentPreference();
    }

    public static boolean nfcEventListener() {
        return FEATURE_FLAGS.nfcEventListener();
    }

    public static boolean nfcObserveMode() {
        return FEATURE_FLAGS.nfcObserveMode();
    }

    public static boolean nfcObserveModeStShim() {
        return FEATURE_FLAGS.nfcObserveModeStShim();
    }

    public static boolean nfcOemExtension() {
        return FEATURE_FLAGS.nfcOemExtension();
    }

    public static boolean nfcOverrideRecoverRoutingTable() {
        return FEATURE_FLAGS.nfcOverrideRecoverRoutingTable();
    }

    public static boolean nfcPersistLog() {
        return FEATURE_FLAGS.nfcPersistLog();
    }

    public static boolean nfcReadPollingLoop() {
        return FEATURE_FLAGS.nfcReadPollingLoop();
    }

    public static boolean nfcReadPollingLoopStShim() {
        return FEATURE_FLAGS.nfcReadPollingLoopStShim();
    }

    public static boolean nfcSetDefaultDiscTech() {
        return FEATURE_FLAGS.nfcSetDefaultDiscTech();
    }

    public static boolean nfcSetServiceEnabledForCategoryOther() {
        return FEATURE_FLAGS.nfcSetServiceEnabledForCategoryOther();
    }

    public static boolean nfcStateChange() {
        return FEATURE_FLAGS.nfcStateChange();
    }

    public static boolean nfcStateChangeSecurityLogEventEnabled() {
        return FEATURE_FLAGS.nfcStateChangeSecurityLogEventEnabled();
    }

    public static boolean nfcVendorCmd() {
        return FEATURE_FLAGS.nfcVendorCmd();
    }

    public static boolean nfcWatchdog() {
        return FEATURE_FLAGS.nfcWatchdog();
    }
}
