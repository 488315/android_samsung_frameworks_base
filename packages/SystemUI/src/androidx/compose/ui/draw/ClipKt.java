package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shape;

/* loaded from: classes.dex */
public abstract class ClipKt {
    public static final Modifier clip(Modifier modifier, Shape shape) {
        return GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, shape, true, 0, 518143);
    }

    public static final Modifier clipToBounds(Modifier modifier) {
        return GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(modifier, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, true, 0, 520191);
    }
}
