package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean adaptiveHandwritingBounds();

    boolean compatchangeForZerojankproxy();

    boolean concurrentInputMethods();

    boolean connectionlessHandwriting();

    boolean consistentGetCurrentInputMethodInfo();

    boolean ctrlShiftShortcut();

    boolean deferShowSoftInputUntilSessionCreation();

    boolean disallowDisablingImeNavigationBar();

    boolean editorinfoHandwritingEnabled();

    boolean fallbackDisplayForSecondaryUserOnSecondaryDisplay();

    boolean homeScreenHandwritingDelegator();

    boolean imeSwitcherRevamp();

    boolean imeSwitcherRevampApi();

    boolean initiationWithoutInputConnection();

    boolean invalidateInputCallsRestart();

    boolean lowerImeOomImportance();

    boolean predictiveBackIme();

    boolean publicAutofillIdInEditorinfo();

    boolean refactorInsetsController();

    boolean reportAnimatingInsetsTypes();

    boolean useHandwritingListenerForTooltype();

    boolean useInputMethodInfoSafeList();

    boolean useZeroJankProxy();

    boolean verifyKeyEvent();

    boolean writingTools();
}
