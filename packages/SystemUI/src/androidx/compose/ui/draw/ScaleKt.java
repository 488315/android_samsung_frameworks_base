package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScaleKt {
    public static final Modifier scale(Modifier modifier, float f, float f2) {
        return (f == 1.0f && f2 == 1.0f) ? modifier : GraphicsLayerModifierKt.m477graphicsLayer_6ThJ44$default(modifier, f, f2, 0.0f, 0.0f, 0.0f, null, false, 0, 524284);
    }
}
