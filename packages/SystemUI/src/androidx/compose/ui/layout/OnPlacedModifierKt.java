package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class OnPlacedModifierKt {
    public static final Modifier onPlaced(Modifier modifier, Function1 function1) {
        return modifier.then(new OnPlacedElement(function1));
    }
}
