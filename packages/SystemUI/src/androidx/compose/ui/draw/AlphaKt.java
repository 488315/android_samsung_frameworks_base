package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AlphaKt {
    public static final Modifier alpha(Modifier modifier, float f) {
        return f == 1.0f ? modifier : GraphicsLayerModifierKt.m477graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, f, 0.0f, 0.0f, null, true, 0, 520187);
    }
}
