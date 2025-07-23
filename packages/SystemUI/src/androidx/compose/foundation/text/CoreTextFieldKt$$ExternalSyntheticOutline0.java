package androidx.compose.foundation.text;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.ui.focus.FocusRequester;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract /* synthetic */ class CoreTextFieldKt$$ExternalSyntheticOutline0 {
    public static FocusRequester m(ComposerImpl composerImpl) {
        FocusRequester focusRequester = new FocusRequester();
        composerImpl.updateRememberedValue(focusRequester);
        return focusRequester;
    }
}
