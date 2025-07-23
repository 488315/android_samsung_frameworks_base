package androidx.compose.ui.input.key;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyInputModifierKt {
    public static final Modifier onKeyEvent(Modifier modifier, Function1 function1) {
        return modifier.then(new KeyInputElement(function1, null));
    }

    public static final Modifier onPreviewKeyEvent(Modifier modifier, Function1 function1) {
        return modifier.then(new KeyInputElement(null, function1));
    }
}
