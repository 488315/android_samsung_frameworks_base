package androidx.compose.foundation.text;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.ui.focus.FocusRequester;

/* loaded from: classes.dex */
public abstract /* synthetic */ class CoreTextFieldKt$$ExternalSyntheticOutline0 {
    public static FocusRequester m(ComposerImpl composerImpl) {
        FocusRequester focusRequester = new FocusRequester();
        composerImpl.updateRememberedValue(focusRequester);
        return focusRequester;
    }
}
