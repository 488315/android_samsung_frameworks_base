package android.view.inputmethod;

public interface FeatureFlags {
    boolean concurrentInputMethods();

    boolean connectionlessHandwriting();

    boolean ctrlShiftShortcut();

    boolean deferShowSoftInputUntilSessionCreation();

    boolean editorinfoHandwritingEnabled();

    boolean homeScreenHandwritingDelegator();

    boolean imeSwitcherRevamp();

    boolean immUserhandleHostsidetests();

    boolean initiationWithoutInputConnection();

    boolean predictiveBackIme();

    boolean refactorInsetsController();

    boolean useHandwritingListenerForTooltype();

    boolean useInputMethodInfoSafeList();

    boolean useZeroJankProxy();
}
