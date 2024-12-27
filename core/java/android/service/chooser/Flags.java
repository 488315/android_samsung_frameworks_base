package android.service.chooser;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CHOOSER_ALBUM_TEXT =
            "android.service.chooser.chooser_album_text";
    public static final String FLAG_CHOOSER_PAYLOAD_TOGGLING =
            "android.service.chooser.chooser_payload_toggling";
    public static final String FLAG_ENABLE_CHOOSER_RESULT =
            "android.service.chooser.enable_chooser_result";
    public static final String FLAG_ENABLE_SHARESHEET_METADATA_EXTRA =
            "android.service.chooser.enable_sharesheet_metadata_extra";
    public static final String FLAG_FIX_RESOLVER_MEMORY_LEAK =
            "android.service.chooser.fix_resolver_memory_leak";

    public static boolean chooserAlbumText() {
        return FEATURE_FLAGS.chooserAlbumText();
    }

    public static boolean chooserPayloadToggling() {
        return FEATURE_FLAGS.chooserPayloadToggling();
    }

    public static boolean enableChooserResult() {
        return FEATURE_FLAGS.enableChooserResult();
    }

    public static boolean enableSharesheetMetadataExtra() {
        return FEATURE_FLAGS.enableSharesheetMetadataExtra();
    }

    public static boolean fixResolverMemoryLeak() {
        return FEATURE_FLAGS.fixResolverMemoryLeak();
    }
}
