package android.content.res;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALWAYS_FALSE = "android.content.res.always_false";
    public static final String FLAG_ASSET_FILE_DESCRIPTOR_FRRO = "android.content.res.asset_file_descriptor_frro";
    public static final String FLAG_DEFAULT_LOCALE = "android.content.res.default_locale";
    public static final String FLAG_DIMENSION_FRRO = "android.content.res.dimension_frro";
    public static final String FLAG_FONT_SCALE_CONVERTER_PUBLIC = "android.content.res.font_scale_converter_public";
    public static final String FLAG_HANDLE_ALL_CONFIG_CHANGES = "android.content.res.handle_all_config_changes";
    public static final String FLAG_LAYOUT_READWRITE_FLAGS = "android.content.res.layout_readwrite_flags";
    public static final String FLAG_MANIFEST_FLAGGING = "android.content.res.manifest_flagging";
    public static final String FLAG_NINE_PATCH_FRRO = "android.content.res.nine_patch_frro";
    public static final String FLAG_REGISTER_RESOURCE_PATHS = "android.content.res.register_resource_paths";
    public static final String FLAG_RESOURCES_MINOR_VERSION_SUPPORT = "android.content.res.resources_minor_version_support";
    public static final String FLAG_RRO_CONSTRAINTS = "android.content.res.rro_constraints";
    public static final String FLAG_RRO_CONTROL_FOR_ANDROID_NO_OVERLAYABLE = "android.content.res.rro_control_for_android_no_overlayable";
    public static final String FLAG_SELF_TARGETING_ANDROID_RESOURCE_FRRO = "android.content.res.self_targeting_android_resource_frro";
    public static final String FLAG_SYSTEM_CONTEXT_HANDLE_APP_INFO_CHANGED = "android.content.res.system_context_handle_app_info_changed";
    public static final String FLAG_USE_NEW_ACONFIG_STORAGE = "android.content.res.use_new_aconfig_storage";

    public static boolean alwaysFalse() {
        return FEATURE_FLAGS.alwaysFalse();
    }

    public static boolean assetFileDescriptorFrro() {
        return FEATURE_FLAGS.assetFileDescriptorFrro();
    }

    public static boolean defaultLocale() {
        return FEATURE_FLAGS.defaultLocale();
    }

    public static boolean dimensionFrro() {
        return FEATURE_FLAGS.dimensionFrro();
    }

    public static boolean fontScaleConverterPublic() {
        return FEATURE_FLAGS.fontScaleConverterPublic();
    }

    public static boolean handleAllConfigChanges() {
        return FEATURE_FLAGS.handleAllConfigChanges();
    }

    public static boolean layoutReadwriteFlags() {
        return FEATURE_FLAGS.layoutReadwriteFlags();
    }

    public static boolean manifestFlagging() {
        return FEATURE_FLAGS.manifestFlagging();
    }

    public static boolean ninePatchFrro() {
        return FEATURE_FLAGS.ninePatchFrro();
    }

    public static boolean registerResourcePaths() {
        return FEATURE_FLAGS.registerResourcePaths();
    }

    public static boolean resourcesMinorVersionSupport() {
        return FEATURE_FLAGS.resourcesMinorVersionSupport();
    }

    public static boolean rroConstraints() {
        return FEATURE_FLAGS.rroConstraints();
    }

    public static boolean rroControlForAndroidNoOverlayable() {
        return FEATURE_FLAGS.rroControlForAndroidNoOverlayable();
    }

    public static boolean selfTargetingAndroidResourceFrro() {
        return FEATURE_FLAGS.selfTargetingAndroidResourceFrro();
    }

    public static boolean systemContextHandleAppInfoChanged() {
        return FEATURE_FLAGS.systemContextHandleAppInfoChanged();
    }

    public static boolean useNewAconfigStorage() {
        return FEATURE_FLAGS.useNewAconfigStorage();
    }
}
