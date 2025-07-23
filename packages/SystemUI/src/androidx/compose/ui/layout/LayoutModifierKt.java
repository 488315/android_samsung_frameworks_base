package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LayoutModifierKt {
    public static final Modifier layout(Modifier modifier, Function3 function3) {
        return modifier.then(new LayoutElement(function3));
    }
}
