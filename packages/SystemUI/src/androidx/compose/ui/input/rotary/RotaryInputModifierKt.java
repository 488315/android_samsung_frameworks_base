package androidx.compose.ui.input.rotary;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class RotaryInputModifierKt {
    public static final Modifier onRotaryScrollEvent(Modifier.Companion companion, Function1 function1) {
        RotaryInputElement rotaryInputElement = new RotaryInputElement(function1, null);
        companion.getClass();
        return rotaryInputElement;
    }
}
