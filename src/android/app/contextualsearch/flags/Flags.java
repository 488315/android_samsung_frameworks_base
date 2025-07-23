package android.app.contextualsearch.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CONTEXTUAL_SEARCH_PREVENT_SELF_CAPTURE = "android.app.contextualsearch.flags.contextual_search_prevent_self_capture";
    public static final String FLAG_ENABLE_SERVICE = "android.app.contextualsearch.flags.enable_service";
    public static final String FLAG_ENABLE_TOKEN_REFRESH = "android.app.contextualsearch.flags.enable_token_refresh";
    public static final String FLAG_INCLUDE_AUDIO_PLAYING_STATUS = "android.app.contextualsearch.flags.include_audio_playing_status";
    public static final String FLAG_MULTI_WINDOW_SCREEN_CONTEXT = "android.app.contextualsearch.flags.multi_window_screen_context";
    public static final String FLAG_REPORT_SECURE_SURFACES_IN_ASSIST_STRUCTURE = "android.app.contextualsearch.flags.report_secure_surfaces_in_assist_structure";
    public static final String FLAG_SELF_INVOCATION = "android.app.contextualsearch.flags.self_invocation";

    public static boolean contextualSearchPreventSelfCapture() {
        return FEATURE_FLAGS.contextualSearchPreventSelfCapture();
    }

    public static boolean enableService() {
        return FEATURE_FLAGS.enableService();
    }

    public static boolean enableTokenRefresh() {
        return FEATURE_FLAGS.enableTokenRefresh();
    }

    public static boolean includeAudioPlayingStatus() {
        return FEATURE_FLAGS.includeAudioPlayingStatus();
    }

    public static boolean multiWindowScreenContext() {
        return FEATURE_FLAGS.multiWindowScreenContext();
    }

    public static boolean reportSecureSurfacesInAssistStructure() {
        return FEATURE_FLAGS.reportSecureSurfacesInAssistStructure();
    }

    public static boolean selfInvocation() {
        return FEATURE_FLAGS.selfInvocation();
    }
}
