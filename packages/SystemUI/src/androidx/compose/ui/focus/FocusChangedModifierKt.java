package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class FocusChangedModifierKt {
    public static final Modifier onFocusChanged(Modifier modifier, Function1 function1) {
        return modifier.then(new FocusChangedElement(function1));
    }
}
