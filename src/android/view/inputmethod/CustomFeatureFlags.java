package android.view.inputmethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADAPTIVE_HANDWRITING_BOUNDS, Flags.FLAG_COMPATCHANGE_FOR_ZEROJANKPROXY, Flags.FLAG_CONCURRENT_INPUT_METHODS, Flags.FLAG_CONNECTIONLESS_HANDWRITING, Flags.FLAG_CONSISTENT_GET_CURRENT_INPUT_METHOD_INFO, Flags.FLAG_CTRL_SHIFT_SHORTCUT, Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION, Flags.FLAG_DISALLOW_DISABLING_IME_NAVIGATION_BAR, Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED, Flags.FLAG_FALLBACK_DISPLAY_FOR_SECONDARY_USER_ON_SECONDARY_DISPLAY, Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR, Flags.FLAG_IME_SWITCHER_REVAMP, Flags.FLAG_IME_SWITCHER_REVAMP_API, Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION, Flags.FLAG_INVALIDATE_INPUT_CALLS_RESTART, Flags.FLAG_LOWER_IME_OOM_IMPORTANCE, Flags.FLAG_PREDICTIVE_BACK_IME, Flags.FLAG_PUBLIC_AUTOFILL_ID_IN_EDITORINFO, Flags.FLAG_REFACTOR_INSETS_CONTROLLER, Flags.FLAG_REPORT_ANIMATING_INSETS_TYPES, Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE, Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST, Flags.FLAG_USE_ZERO_JANK_PROXY, Flags.FLAG_VERIFY_KEY_EVENT, Flags.FLAG_WRITING_TOOLS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean adaptiveHandwritingBounds() {
        return getValue(Flags.FLAG_ADAPTIVE_HANDWRITING_BOUNDS, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adaptiveHandwritingBounds();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean compatchangeForZerojankproxy() {
        return getValue(Flags.FLAG_COMPATCHANGE_FOR_ZEROJANKPROXY, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).compatchangeForZerojankproxy();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean concurrentInputMethods() {
        return getValue(Flags.FLAG_CONCURRENT_INPUT_METHODS, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concurrentInputMethods();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean connectionlessHandwriting() {
        return getValue(Flags.FLAG_CONNECTIONLESS_HANDWRITING, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).connectionlessHandwriting();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean consistentGetCurrentInputMethodInfo() {
        return getValue(Flags.FLAG_CONSISTENT_GET_CURRENT_INPUT_METHOD_INFO, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).consistentGetCurrentInputMethodInfo();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean ctrlShiftShortcut() {
        return getValue(Flags.FLAG_CTRL_SHIFT_SHORTCUT, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ctrlShiftShortcut();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean deferShowSoftInputUntilSessionCreation() {
        return getValue(Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deferShowSoftInputUntilSessionCreation();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean disallowDisablingImeNavigationBar() {
        return getValue(Flags.FLAG_DISALLOW_DISABLING_IME_NAVIGATION_BAR, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disallowDisablingImeNavigationBar();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean editorinfoHandwritingEnabled() {
        return getValue(Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).editorinfoHandwritingEnabled();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean fallbackDisplayForSecondaryUserOnSecondaryDisplay() {
        return getValue(Flags.FLAG_FALLBACK_DISPLAY_FOR_SECONDARY_USER_ON_SECONDARY_DISPLAY, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fallbackDisplayForSecondaryUserOnSecondaryDisplay();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean homeScreenHandwritingDelegator() {
        return getValue(Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).homeScreenHandwritingDelegator();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean imeSwitcherRevamp() {
        return getValue(Flags.FLAG_IME_SWITCHER_REVAMP, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).imeSwitcherRevamp();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean imeSwitcherRevampApi() {
        return getValue(Flags.FLAG_IME_SWITCHER_REVAMP_API, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).imeSwitcherRevampApi();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean initiationWithoutInputConnection() {
        return getValue(Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).initiationWithoutInputConnection();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean invalidateInputCallsRestart() {
        return getValue(Flags.FLAG_INVALIDATE_INPUT_CALLS_RESTART, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).invalidateInputCallsRestart();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean lowerImeOomImportance() {
        return getValue(Flags.FLAG_LOWER_IME_OOM_IMPORTANCE, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).lowerImeOomImportance();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean predictiveBackIme() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_IME, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackIme();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean publicAutofillIdInEditorinfo() {
        return getValue(Flags.FLAG_PUBLIC_AUTOFILL_ID_IN_EDITORINFO, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).publicAutofillIdInEditorinfo();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean refactorInsetsController() {
        return getValue(Flags.FLAG_REFACTOR_INSETS_CONTROLLER, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refactorInsetsController();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean reportAnimatingInsetsTypes() {
        return getValue(Flags.FLAG_REPORT_ANIMATING_INSETS_TYPES, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reportAnimatingInsetsTypes();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useHandwritingListenerForTooltype() {
        return getValue(Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useHandwritingListenerForTooltype();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useInputMethodInfoSafeList() {
        return getValue(Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useInputMethodInfoSafeList();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useZeroJankProxy() {
        return getValue(Flags.FLAG_USE_ZERO_JANK_PROXY, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useZeroJankProxy();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean verifyKeyEvent() {
        return getValue(Flags.FLAG_VERIFY_KEY_EVENT, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).verifyKeyEvent();
            }
        });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean writingTools() {
        return getValue(Flags.FLAG_WRITING_TOOLS, new Predicate() { // from class: android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).writingTools();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADAPTIVE_HANDWRITING_BOUNDS, Flags.FLAG_COMPATCHANGE_FOR_ZEROJANKPROXY, Flags.FLAG_CONCURRENT_INPUT_METHODS, Flags.FLAG_CONNECTIONLESS_HANDWRITING, Flags.FLAG_CONSISTENT_GET_CURRENT_INPUT_METHOD_INFO, Flags.FLAG_CTRL_SHIFT_SHORTCUT, Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION, Flags.FLAG_DISALLOW_DISABLING_IME_NAVIGATION_BAR, Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED, Flags.FLAG_FALLBACK_DISPLAY_FOR_SECONDARY_USER_ON_SECONDARY_DISPLAY, Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR, Flags.FLAG_IME_SWITCHER_REVAMP, Flags.FLAG_IME_SWITCHER_REVAMP_API, Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION, Flags.FLAG_INVALIDATE_INPUT_CALLS_RESTART, Flags.FLAG_LOWER_IME_OOM_IMPORTANCE, Flags.FLAG_PREDICTIVE_BACK_IME, Flags.FLAG_PUBLIC_AUTOFILL_ID_IN_EDITORINFO, Flags.FLAG_REFACTOR_INSETS_CONTROLLER, Flags.FLAG_REPORT_ANIMATING_INSETS_TYPES, Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE, Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST, Flags.FLAG_USE_ZERO_JANK_PROXY, Flags.FLAG_VERIFY_KEY_EVENT, Flags.FLAG_WRITING_TOOLS);
    }
}
