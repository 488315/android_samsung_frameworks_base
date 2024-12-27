package android.nfc;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_NFC_CHARGING = "android.nfc.enable_nfc_charging";
    public static final String FLAG_ENABLE_NFC_MAINLINE = "android.nfc.enable_nfc_mainline";
    public static final String FLAG_ENABLE_NFC_READER_OPTION =
            "android.nfc.enable_nfc_reader_option";
    public static final String FLAG_ENABLE_NFC_SET_DISCOVERY_TECH =
            "android.nfc.enable_nfc_set_discovery_tech";
    public static final String FLAG_ENABLE_NFC_USER_RESTRICTION =
            "android.nfc.enable_nfc_user_restriction";
    public static final String FLAG_ENABLE_TAG_DETECTION_BROADCASTS =
            "android.nfc.enable_tag_detection_broadcasts";
    public static final String FLAG_NFC_OBSERVE_MODE = "android.nfc.nfc_observe_mode";
    public static final String FLAG_NFC_OBSERVE_MODE_ST_SHIM =
            "android.nfc.nfc_observe_mode_st_shim";
    public static final String FLAG_NFC_OEM_EXTENSION = "android.nfc.nfc_oem_extension";
    public static final String FLAG_NFC_READ_POLLING_LOOP = "android.nfc.nfc_read_polling_loop";
    public static final String FLAG_NFC_READ_POLLING_LOOP_ST_SHIM =
            "android.nfc.nfc_read_polling_loop_st_shim";
    public static final String FLAG_NFC_SET_DEFAULT_DISC_TECH =
            "android.nfc.nfc_set_default_disc_tech";
    public static final String FLAG_NFC_STATE_CHANGE = "android.nfc.nfc_state_change";
    public static final String FLAG_NFC_VENDOR_CMD = "android.nfc.nfc_vendor_cmd";

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

    public static boolean nfcObserveMode() {
        return FEATURE_FLAGS.nfcObserveMode();
    }

    public static boolean nfcObserveModeStShim() {
        return FEATURE_FLAGS.nfcObserveModeStShim();
    }

    public static boolean nfcOemExtension() {
        return FEATURE_FLAGS.nfcOemExtension();
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

    public static boolean nfcStateChange() {
        return FEATURE_FLAGS.nfcStateChange();
    }

    public static boolean nfcVendorCmd() {
        return FEATURE_FLAGS.nfcVendorCmd();
    }
}
