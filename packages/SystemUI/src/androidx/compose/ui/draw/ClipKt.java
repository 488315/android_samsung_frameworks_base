package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ClipKt {
    public static final Modifier clip(Modifier modifier, Shape shape) {
        return GraphicsLayerModifierKt.m477graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, shape, true, 0, 518143);
    }

    public static final Modifier clipToBounds(Modifier modifier) {
        return GraphicsLayerModifierKt.m477graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, true, 0, 520191);
    }
}
