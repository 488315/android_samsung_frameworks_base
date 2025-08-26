package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class FocusableKt {
    public static final Modifier focusable(MutableInteractionSource mutableInteractionSource, Modifier modifier, boolean z) {
        return modifier.then(z ? new FocusableElement(mutableInteractionSource) : Modifier.Companion);
    }

    public static /* synthetic */ Modifier focusable$default(Modifier modifier, boolean z, MutableInteractionSource mutableInteractionSource, int i) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            mutableInteractionSource = null;
        }
        return focusable(mutableInteractionSource, modifier, z);
    }
}
