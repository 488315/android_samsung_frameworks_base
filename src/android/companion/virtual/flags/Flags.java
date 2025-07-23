package android.companion.virtual.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CROSS_DEVICE_CLIPBOARD = "android.companion.virtual.flags.cross_device_clipboard";
    public static final String FLAG_DYNAMIC_POLICY = "android.companion.virtual.flags.dynamic_policy";
    public static final String FLAG_PERSISTENT_DEVICE_ID_API = "android.companion.virtual.flags.persistent_device_id_api";
    public static final String FLAG_VDM_CUSTOM_HOME = "android.companion.virtual.flags.vdm_custom_home";
    public static final String FLAG_VDM_CUSTOM_IME = "android.companion.virtual.flags.vdm_custom_ime";
    public static final String FLAG_VDM_PUBLIC_APIS = "android.companion.virtual.flags.vdm_public_apis";
    public static final String FLAG_VIRTUAL_CAMERA = "android.companion.virtual.flags.virtual_camera";
    public static final String FLAG_VIRTUAL_STYLUS = "android.companion.virtual.flags.virtual_stylus";

    public static boolean crossDeviceClipboard() {
        return FEATURE_FLAGS.crossDeviceClipboard();
    }

    public static boolean dynamicPolicy() {
        return FEATURE_FLAGS.dynamicPolicy();
    }

    public static boolean persistentDeviceIdApi() {
        return FEATURE_FLAGS.persistentDeviceIdApi();
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
