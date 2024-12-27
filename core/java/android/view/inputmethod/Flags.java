package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CONCURRENT_INPUT_METHODS = "android.view.inputmethod.concurrent_input_methods";
    public static final String FLAG_CONNECTIONLESS_HANDWRITING = "android.view.inputmethod.connectionless_handwriting";
    public static final String FLAG_CTRL_SHIFT_SHORTCUT = "android.view.inputmethod.ctrl_shift_shortcut";
    public static final String FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION = "android.view.inputmethod.defer_show_soft_input_until_session_creation";
    public static final String FLAG_EDITORINFO_HANDWRITING_ENABLED = "android.view.inputmethod.editorinfo_handwriting_enabled";
    public static final String FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR = "android.view.inputmethod.home_screen_handwriting_delegator";
    public static final String FLAG_IME_SWITCHER_REVAMP = "android.view.inputmethod.ime_switcher_revamp";
    public static final String FLAG_IMM_USERHANDLE_HOSTSIDETESTS = "android.view.inputmethod.imm_userhandle_hostsidetests";
    public static final String FLAG_INITIATION_WITHOUT_INPUT_CONNECTION = "android.view.inputmethod.initiation_without_input_connection";
    public static final String FLAG_PREDICTIVE_BACK_IME = "android.view.inputmethod.predictive_back_ime";
    public static final String FLAG_REFACTOR_INSETS_CONTROLLER = "android.view.inputmethod.refactor_insets_controller";
    public static final String FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE = "android.view.inputmethod.use_handwriting_listener_for_tooltype";
    public static final String FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST = "android.view.inputmethod.use_input_method_info_safe_list";
    public static final String FLAG_USE_ZERO_JANK_PROXY = "android.view.inputmethod.use_zero_jank_proxy";

    public static boolean concurrentInputMethods() {
        return FEATURE_FLAGS.concurrentInputMethods();
    }

    public static boolean connectionlessHandwriting() {
        return FEATURE_FLAGS.connectionlessHandwriting();
    }

    public static boolean ctrlShiftShortcut() {
        return FEATURE_FLAGS.ctrlShiftShortcut();
    }

    public static boolean deferShowSoftInputUntilSessionCreation() {
        return FEATURE_FLAGS.deferShowSoftInputUntilSessionCreation();
    }

    public static boolean editorinfoHandwritingEnabled() {
        return FEATURE_FLAGS.editorinfoHandwritingEnabled();
    }

    public static boolean homeScreenHandwritingDelegator() {
        return FEATURE_FLAGS.homeScreenHandwritingDelegator();
    }

    public static boolean imeSwitcherRevamp() {
        return FEATURE_FLAGS.imeSwitcherRevamp();
    }

    public static boolean immUserhandleHostsidetests() {
        return FEATURE_FLAGS.immUserhandleHostsidetests();
    }

    public static boolean initiationWithoutInputConnection() {
        return FEATURE_FLAGS.initiationWithoutInputConnection();
    }

    public static boolean predictiveBackIme() {
        return FEATURE_FLAGS.predictiveBackIme();
    }

    public static boolean refactorInsetsController() {
        return FEATURE_FLAGS.refactorInsetsController();
    }

    public static boolean useHandwritingListenerForTooltype() {
        return FEATURE_FLAGS.useHandwritingListenerForTooltype();
    }

    public static boolean useInputMethodInfoSafeList() {
        return FEATURE_FLAGS.useInputMethodInfoSafeList();
    }

    public static boolean useZeroJankProxy() {
        return FEATURE_FLAGS.useZeroJankProxy();
    }
}
