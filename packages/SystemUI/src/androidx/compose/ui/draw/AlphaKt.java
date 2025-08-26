package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;

/* loaded from: classes.dex */
public abstract class AlphaKt {
    public static final Modifier alpha(Modifier modifier, float f) {
        return f == 1.0f ? modifier : GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, f, 0.0f, 0.0f, null, true, 0, 520187);
    }
}
