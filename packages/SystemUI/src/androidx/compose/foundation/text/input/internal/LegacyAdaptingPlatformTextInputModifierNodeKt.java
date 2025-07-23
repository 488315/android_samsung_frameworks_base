package androidx.compose.foundation.text.input.internal;

import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LegacyAdaptingPlatformTextInputModifierNodeKt {
    public static final Modifier legacyTextInputAdapter(Modifier modifier, LegacyPlatformTextInputServiceAdapter legacyPlatformTextInputServiceAdapter, LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager) {
        return modifier.then(new LegacyAdaptingPlatformTextInputModifier(legacyPlatformTextInputServiceAdapter, legacyTextFieldState, textFieldSelectionManager));
    }
}
