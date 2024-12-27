package android.view.inputmethod;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.view.inputmethod.FeatureFlags
    public boolean concurrentInputMethods() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean connectionlessHandwriting() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean ctrlShiftShortcut() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean deferShowSoftInputUntilSessionCreation() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean editorinfoHandwritingEnabled() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean homeScreenHandwritingDelegator() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean imeSwitcherRevamp() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean immUserhandleHostsidetests() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean initiationWithoutInputConnection() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean predictiveBackIme() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean refactorInsetsController() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useHandwritingListenerForTooltype() {
        return false;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useInputMethodInfoSafeList() {
        return true;
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useZeroJankProxy() {
        return true;
    }
}
