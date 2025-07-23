package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OnGloballyPositionedModifierKt {
    public static final Modifier onGloballyPositioned(Modifier modifier, Function1 function1) {
        return modifier.then(new OnGloballyPositionedElement(function1));
    }
}
