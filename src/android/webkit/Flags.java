package android.webkit;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEPRECATE_START_SAFE_BROWSING = "android.webkit.deprecate_start_safe_browsing";
    public static final String FLAG_FILE_SYSTEM_ACCESS = "android.webkit.file_system_access";
    public static final String FLAG_MAINLINE_APIS = "android.webkit.mainline_apis";
    public static final String FLAG_UPDATE_SERVICE_IPC_WRAPPER = "android.webkit.update_service_ipc_wrapper";
    public static final String FLAG_UPDATE_SERVICE_V2 = "android.webkit.update_service_v2";
    public static final String FLAG_USER_AGENT_REDUCTION = "android.webkit.user_agent_reduction";
    public static final String FLAG_USE_B_ENTRY_POINT = "android.webkit.use_b_entry_point";

    public static boolean deprecateStartSafeBrowsing() {
        return FEATURE_FLAGS.deprecateStartSafeBrowsing();
    }

    public static boolean fileSystemAccess() {
        return FEATURE_FLAGS.fileSystemAccess();
    }

    public static boolean mainlineApis() {
        return FEATURE_FLAGS.mainlineApis();
    }

    public static boolean updateServiceIpcWrapper() {
        return FEATURE_FLAGS.updateServiceIpcWrapper();
    }

    public static boolean updateServiceV2() {
        return FEATURE_FLAGS.updateServiceV2();
    }

    public static boolean useBEntryPoint() {
        return FEATURE_FLAGS.useBEntryPoint();
    }

    public static boolean userAgentReduction() {
        return FEATURE_FLAGS.userAgentReduction();
    }
}
