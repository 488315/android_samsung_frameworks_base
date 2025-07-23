package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.platform.InspectableValueKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BackgroundKt {
    /* renamed from: background-bw27NRU, reason: not valid java name */
    public static final Modifier m26backgroundbw27NRU(Modifier modifier, long j, Shape shape) {
        return modifier.then(new BackgroundElement(j, null, 1.0f, shape, InspectableValueKt.NoInspectorInfo, 2, null));
    }
}
