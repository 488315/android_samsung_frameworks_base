package androidx.compose.ui.modifier;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public interface ModifierLocalProvider<T> extends Modifier.Element {
    ProvidableModifierLocal getKey();

    WindowInsets getValue$1();
}
