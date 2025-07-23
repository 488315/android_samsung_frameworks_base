package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADAPTIVE_HANDWRITING_BOUNDS = "android.view.inputmethod.adaptive_handwriting_bounds";
    public static final String FLAG_COMPATCHANGE_FOR_ZEROJANKPROXY = "android.view.inputmethod.compatchange_for_zerojankproxy";
    public static final String FLAG_CONCURRENT_INPUT_METHODS = "android.view.inputmethod.concurrent_input_methods";
    public static final String FLAG_CONNECTIONLESS_HANDWRITING = "android.view.inputmethod.connectionless_handwriting";
    public static final String FLAG_CONSISTENT_GET_CURRENT_INPUT_METHOD_INFO = "android.view.inputmethod.consistent_get_current_input_method_info";
    public static final String FLAG_CTRL_SHIFT_SHORTCUT = "android.view.inputmethod.ctrl_shift_shortcut";
    public static final String FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION = "android.view.inputmethod.defer_show_soft_input_until_session_creation";
    public static final String FLAG_DISALLOW_DISABLING_IME_NAVIGATION_BAR = "android.view.inputmethod.disallow_disabling_ime_navigation_bar";
    public static final String FLAG_EDITORINFO_HANDWRITING_ENABLED = "android.view.inputmethod.editorinfo_handwriting_enabled";
    public static final String FLAG_FALLBACK_DISPLAY_FOR_SECONDARY_USER_ON_SECONDARY_DISPLAY = "android.view.inputmethod.fallback_display_for_secondary_user_on_secondary_display";
    public static final String FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR = "android.view.inputmethod.home_screen_handwriting_delegator";
    public static final String FLAG_IME_SWITCHER_REVAMP = "android.view.inputmethod.ime_switcher_revamp";
    public static final String FLAG_IME_SWITCHER_REVAMP_API = "android.view.inputmethod.ime_switcher_revamp_api";
    public static final String FLAG_INITIATION_WITHOUT_INPUT_CONNECTION = "android.view.inputmethod.initiation_without_input_connection";
    public static final String FLAG_INVALIDATE_INPUT_CALLS_RESTART = "android.view.inputmethod.invalidate_input_calls_restart";
    public static final String FLAG_LOWER_IME_OOM_IMPORTANCE = "android.view.inputmethod.lower_ime_oom_importance";
    public static final String FLAG_PREDICTIVE_BACK_IME = "android.view.inputmethod.predictive_back_ime";
    public static final String FLAG_PUBLIC_AUTOFILL_ID_IN_EDITORINFO = "android.view.inputmethod.public_autofill_id_in_editorinfo";
    public static final String FLAG_REFACTOR_INSETS_CONTROLLER = "android.view.inputmethod.refactor_insets_controller";
    public static final String FLAG_REPORT_ANIMATING_INSETS_TYPES = "android.view.inputmethod.report_animating_insets_types";
    public static final String FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE = "android.view.inputmethod.use_handwriting_listener_for_tooltype";
    public static final String FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST = "android.view.inputmethod.use_input_method_info_safe_list";
    public static final String FLAG_USE_ZERO_JANK_PROXY = "android.view.inputmethod.use_zero_jank_proxy";
    public static final String FLAG_VERIFY_KEY_EVENT = "android.view.inputmethod.verify_key_event";
    public static final String FLAG_WRITING_TOOLS = "android.view.inputmethod.writing_tools";

    public static boolean adaptiveHandwritingBounds() {
        return FEATURE_FLAGS.adaptiveHandwritingBounds();
    }

    public static boolean compatchangeForZerojankproxy() {
        return FEATURE_FLAGS.compatchangeForZerojankproxy();
    }

    public static boolean concurrentInputMethods() {
        return FEATURE_FLAGS.concurrentInputMethods();
    }

    public static boolean connectionlessHandwriting() {
        return FEATURE_FLAGS.connectionlessHandwriting();
    }

    public static boolean consistentGetCurrentInputMethodInfo() {
        return FEATURE_FLAGS.consistentGetCurrentInputMethodInfo();
    }

    public static boolean ctrlShiftShortcut() {
        return FEATURE_FLAGS.ctrlShiftShortcut();
    }

    public static boolean deferShowSoftInputUntilSessionCreation() {
        return FEATURE_FLAGS.deferShowSoftInputUntilSessionCreation();
    }

    public static boolean disallowDisablingImeNavigationBar() {
        return FEATURE_FLAGS.disallowDisablingImeNavigationBar();
    }

    public static boolean editorinfoHandwritingEnabled() {
        return FEATURE_FLAGS.editorinfoHandwritingEnabled();
    }

    public static boolean fallbackDisplayForSecondaryUserOnSecondaryDisplay() {
        return FEATURE_FLAGS.fallbackDisplayForSecondaryUserOnSecondaryDisplay();
    }

    public static boolean homeScreenHandwritingDelegator() {
        return FEATURE_FLAGS.homeScreenHandwritingDelegator();
    }

    public static boolean imeSwitcherRevamp() {
        return FEATURE_FLAGS.imeSwitcherRevamp();
    }

    public static boolean imeSwitcherRevampApi() {
        return FEATURE_FLAGS.imeSwitcherRevampApi();
    }

    public static boolean initiationWithoutInputConnection() {
        return FEATURE_FLAGS.initiationWithoutInputConnection();
    }

    public static boolean invalidateInputCallsRestart() {
        return FEATURE_FLAGS.invalidateInputCallsRestart();
    }

    public static boolean lowerImeOomImportance() {
        return FEATURE_FLAGS.lowerImeOomImportance();
    }

    public static boolean predictiveBackIme() {
        return FEATURE_FLAGS.predictiveBackIme();
    }

    public static boolean publicAutofillIdInEditorinfo() {
        return FEATURE_FLAGS.publicAutofillIdInEditorinfo();
    }

    public static boolean refactorInsetsController() {
        return FEATURE_FLAGS.refactorInsetsController();
    }

    public static boolean reportAnimatingInsetsTypes() {
        return FEATURE_FLAGS.reportAnimatingInsetsTypes();
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

    public static boolean verifyKeyEvent() {
        return FEATURE_FLAGS.verifyKeyEvent();
    }

    public static boolean writingTools() {
        return FEATURE_FLAGS.writingTools();
    }
}
