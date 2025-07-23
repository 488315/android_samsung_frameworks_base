package androidx.compose.ui.input.rotary;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RotaryInputModifierKt {
    public static final Modifier onRotaryScrollEvent(Modifier.Companion companion, Function1 function1) {
        RotaryInputElement rotaryInputElement = new RotaryInputElement(function1, null);
        companion.getClass();
        return rotaryInputElement;
    }
}
