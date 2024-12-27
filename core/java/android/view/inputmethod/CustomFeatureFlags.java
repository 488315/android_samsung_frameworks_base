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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_CONCURRENT_INPUT_METHODS,
                            Flags.FLAG_CONNECTIONLESS_HANDWRITING,
                            Flags.FLAG_CTRL_SHIFT_SHORTCUT,
                            Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION,
                            Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED,
                            Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR,
                            Flags.FLAG_IME_SWITCHER_REVAMP,
                            Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS,
                            Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION,
                            Flags.FLAG_PREDICTIVE_BACK_IME,
                            Flags.FLAG_REFACTOR_INSETS_CONTROLLER,
                            Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE,
                            Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST,
                            Flags.FLAG_USE_ZERO_JANK_PROXY,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean concurrentInputMethods() {
        return getValue(
                Flags.FLAG_CONCURRENT_INPUT_METHODS,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).concurrentInputMethods();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean connectionlessHandwriting() {
        return getValue(
                Flags.FLAG_CONNECTIONLESS_HANDWRITING,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).connectionlessHandwriting();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean ctrlShiftShortcut() {
        return getValue(
                Flags.FLAG_CTRL_SHIFT_SHORTCUT,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ctrlShiftShortcut();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean deferShowSoftInputUntilSessionCreation() {
        return getValue(
                Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deferShowSoftInputUntilSessionCreation();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean editorinfoHandwritingEnabled() {
        return getValue(
                Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).editorinfoHandwritingEnabled();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean homeScreenHandwritingDelegator() {
        return getValue(
                Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).homeScreenHandwritingDelegator();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean imeSwitcherRevamp() {
        return getValue(
                Flags.FLAG_IME_SWITCHER_REVAMP,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).imeSwitcherRevamp();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean immUserhandleHostsidetests() {
        return getValue(
                Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).immUserhandleHostsidetests();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean initiationWithoutInputConnection() {
        return getValue(
                Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).initiationWithoutInputConnection();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean predictiveBackIme() {
        return getValue(
                Flags.FLAG_PREDICTIVE_BACK_IME,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).predictiveBackIme();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean refactorInsetsController() {
        return getValue(
                Flags.FLAG_REFACTOR_INSETS_CONTROLLER,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).refactorInsetsController();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useHandwritingListenerForTooltype() {
        return getValue(
                Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useHandwritingListenerForTooltype();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useInputMethodInfoSafeList() {
        return getValue(
                Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useInputMethodInfoSafeList();
                    }
                });
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useZeroJankProxy() {
        return getValue(
                Flags.FLAG_USE_ZERO_JANK_PROXY,
                new Predicate() { // from class:
                                  // android.view.inputmethod.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useZeroJankProxy();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_CONCURRENT_INPUT_METHODS,
                Flags.FLAG_CONNECTIONLESS_HANDWRITING,
                Flags.FLAG_CTRL_SHIFT_SHORTCUT,
                Flags.FLAG_DEFER_SHOW_SOFT_INPUT_UNTIL_SESSION_CREATION,
                Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED,
                Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR,
                Flags.FLAG_IME_SWITCHER_REVAMP,
                Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS,
                Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION,
                Flags.FLAG_PREDICTIVE_BACK_IME,
                Flags.FLAG_REFACTOR_INSETS_CONTROLLER,
                Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE,
                Flags.FLAG_USE_INPUT_METHOD_INFO_SAFE_LIST,
                Flags.FLAG_USE_ZERO_JANK_PROXY);
    }
}
