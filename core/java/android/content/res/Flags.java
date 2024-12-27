package android.content.res;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ASSET_FILE_DESCRIPTOR_FRRO =
            "android.content.res.asset_file_descriptor_frro";
    public static final String FLAG_DEFAULT_LOCALE = "android.content.res.default_locale";
    public static final String FLAG_FONT_SCALE_CONVERTER_PUBLIC =
            "android.content.res.font_scale_converter_public";
    public static final String FLAG_MANIFEST_FLAGGING = "android.content.res.manifest_flagging";
    public static final String FLAG_NINE_PATCH_FRRO = "android.content.res.nine_patch_frro";
    public static final String FLAG_REGISTER_RESOURCE_PATHS =
            "android.content.res.register_resource_paths";

    public static boolean assetFileDescriptorFrro() {
        return FEATURE_FLAGS.assetFileDescriptorFrro();
    }

    public static boolean defaultLocale() {
        return FEATURE_FLAGS.defaultLocale();
    }

    public static boolean fontScaleConverterPublic() {
        return FEATURE_FLAGS.fontScaleConverterPublic();
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
}
