package android.companion.virtual.flags;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CONSISTENT_DISPLAY_FLAGS =
            "android.companion.virtual.flags.consistent_display_flags";
    public static final String FLAG_CROSS_DEVICE_CLIPBOARD =
            "android.companion.virtual.flags.cross_device_clipboard";
    public static final String FLAG_DYNAMIC_POLICY =
            "android.companion.virtual.flags.dynamic_policy";
    public static final String FLAG_ENABLE_NATIVE_VDM =
            "android.companion.virtual.flags.enable_native_vdm";
    public static final String FLAG_EXPRESS_METRICS =
            "android.companion.virtual.flags.express_metrics";
    public static final String FLAG_IMPULSE_VELOCITY_STRATEGY_FOR_TOUCH_NAVIGATION =
            "android.companion.virtual.flags.impulse_velocity_strategy_for_touch_navigation";
    public static final String FLAG_INTERACTIVE_SCREEN_MIRROR =
            "android.companion.virtual.flags.interactive_screen_mirror";
    public static final String FLAG_INTERCEPT_INTENTS_BEFORE_APPLYING_POLICY =
            "android.companion.virtual.flags.intercept_intents_before_applying_policy";
    public static final String FLAG_PERSISTENT_DEVICE_ID_API =
            "android.companion.virtual.flags.persistent_device_id_api";
    public static final String FLAG_STREAM_CAMERA = "android.companion.virtual.flags.stream_camera";
    public static final String FLAG_STREAM_PERMISSIONS =
            "android.companion.virtual.flags.stream_permissions";
    public static final String FLAG_VDM_CUSTOM_HOME =
            "android.companion.virtual.flags.vdm_custom_home";
    public static final String FLAG_VDM_CUSTOM_IME =
            "android.companion.virtual.flags.vdm_custom_ime";
    public static final String FLAG_VDM_PUBLIC_APIS =
            "android.companion.virtual.flags.vdm_public_apis";
    public static final String FLAG_VIRTUAL_CAMERA =
            "android.companion.virtual.flags.virtual_camera";
    public static final String FLAG_VIRTUAL_STYLUS =
            "android.companion.virtual.flags.virtual_stylus";

    public static boolean consistentDisplayFlags() {
        return FEATURE_FLAGS.consistentDisplayFlags();
    }

    public static boolean crossDeviceClipboard() {
        return FEATURE_FLAGS.crossDeviceClipboard();
    }

    public static boolean dynamicPolicy() {
        return FEATURE_FLAGS.dynamicPolicy();
    }

    public static boolean enableNativeVdm() {
        return FEATURE_FLAGS.enableNativeVdm();
    }

    public static boolean expressMetrics() {
        return FEATURE_FLAGS.expressMetrics();
    }

    public static boolean impulseVelocityStrategyForTouchNavigation() {
        return FEATURE_FLAGS.impulseVelocityStrategyForTouchNavigation();
    }

    public static boolean interactiveScreenMirror() {
        return FEATURE_FLAGS.interactiveScreenMirror();
    }

    public static boolean interceptIntentsBeforeApplyingPolicy() {
        return FEATURE_FLAGS.interceptIntentsBeforeApplyingPolicy();
    }

    public static boolean persistentDeviceIdApi() {
        return FEATURE_FLAGS.persistentDeviceIdApi();
    }

    public static boolean streamCamera() {
        return FEATURE_FLAGS.streamCamera();
    }

    public static boolean streamPermissions() {
        return FEATURE_FLAGS.streamPermissions();
    }

    public static boolean vdmCustomHome() {
        return FEATURE_FLAGS.vdmCustomHome();
    }

    public static boolean vdmCustomIme() {
        return FEATURE_FLAGS.vdmCustomIme();
    }

    public static boolean vdmPublicApis() {
        return FEATURE_FLAGS.vdmPublicApis();
    }

    public static boolean virtualCamera() {
        return FEATURE_FLAGS.virtualCamera();
    }

    public static boolean virtualStylus() {
        return FEATURE_FLAGS.virtualStylus();
    }
}
