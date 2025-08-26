package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.platform.InspectableValueKt;

/* loaded from: classes.dex */
public abstract class BackgroundKt {
    /* renamed from: background-bw27NRU, reason: not valid java name */
    public static final Modifier m26backgroundbw27NRU(Modifier modifier, long j, Shape shape) {
        return modifier.then(new BackgroundElement(j, null, 1.0f, shape, InspectableValueKt.NoInspectorInfo, 2, null));
    }
}
