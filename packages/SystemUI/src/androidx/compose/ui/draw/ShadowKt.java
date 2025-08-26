package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerScopeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class ShadowKt {
    /* renamed from: shadow-s4CzXII$default, reason: not valid java name */
    public static Modifier m366shadows4CzXII$default(Modifier modifier, float f, Shape shape, long j, int i) {
        boolean z;
        if ((i & 4) != 0) {
            Dp.Companion companion = Dp.Companion;
            z = Float.compare(f, (float) 0) > 0;
        } else {
            z = false;
        }
        long j2 = GraphicsLayerScopeKt.DefaultShadowColor;
        long j3 = (i & 16) != 0 ? j2 : j;
        Dp.Companion companion2 = Dp.Companion;
        return (Float.compare(f, (float) 0) > 0 || z) ? modifier.then(new ShadowGraphicsLayerElement(f, shape, z, j2, j3, null)) : modifier;
    }
}
