package com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEVICE_STATE_CONFIGURATION_FLAG = "android.hardware.devicestate.feature.flags.device_state_configuration_flag";
    public static final String FLAG_DEVICE_STATE_PROPERTY_API = "android.hardware.devicestate.feature.flags.device_state_property_api";
    public static final String FLAG_DEVICE_STATE_PROPERTY_MIGRATION = "android.hardware.devicestate.feature.flags.device_state_property_migration";
    public static final String FLAG_DEVICE_STATE_RDM_V2 = "android.hardware.devicestate.feature.flags.device_state_rdm_v2";
    public static final String FLAG_DEVICE_STATE_REQUESTER_CANCEL_STATE = "android.hardware.devicestate.feature.flags.device_state_requester_cancel_state";

    public static boolean deviceStateConfigurationFlag() {
        return FEATURE_FLAGS.deviceStateConfigurationFlag();
    }

    public static boolean deviceStatePropertyApi() {
        return FEATURE_FLAGS.deviceStatePropertyApi();
    }

    public static boolean deviceStatePropertyMigration() {
        return FEATURE_FLAGS.deviceStatePropertyMigration();
    }

    public static boolean deviceStateRdmV2() {
        return FEATURE_FLAGS.deviceStateRdmV2();
    }

    public static boolean deviceStateRequesterCancelState() {
        return FEATURE_FLAGS.deviceStateRequesterCancelState();
    }
}
