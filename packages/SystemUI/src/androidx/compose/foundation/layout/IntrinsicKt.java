package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IntrinsicKt {
    public static final Modifier height(Modifier modifier, IntrinsicSize intrinsicSize) {
        return modifier.then(new IntrinsicHeightElement(intrinsicSize, true, InspectableValueKt.NoInspectorInfo));
    }

    public static final Modifier width(Modifier modifier, IntrinsicSize intrinsicSize) {
        return modifier.then(new IntrinsicWidthElement(intrinsicSize, true, InspectableValueKt.NoInspectorInfo));
    }
}
