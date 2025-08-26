package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class HoverableKt {
    public static final Modifier hoverable(MutableInteractionSource mutableInteractionSource, Modifier modifier, boolean z) {
        return modifier.then(z ? new HoverableElement(mutableInteractionSource) : Modifier.Companion);
    }
}
