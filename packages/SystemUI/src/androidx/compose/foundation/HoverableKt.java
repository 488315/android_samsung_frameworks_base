package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class HoverableKt {
    public static final Modifier hoverable(MutableInteractionSource mutableInteractionSource, Modifier modifier, boolean z) {
        return modifier.then(z ? new HoverableElement(mutableInteractionSource) : Modifier.Companion);
    }
}
