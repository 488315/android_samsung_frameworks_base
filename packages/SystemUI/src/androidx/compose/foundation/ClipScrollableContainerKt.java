package androidx.compose.foundation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class ClipScrollableContainerKt {
    public static final float MaxSupportedElevation;

    static {
        Dp.Companion companion = Dp.Companion;
        MaxSupportedElevation = 30;
    }

    public static final Modifier clipScrollableContainer(Modifier modifier, Orientation orientation) {
        return modifier.then(orientation == Orientation.Vertical ? ClipKt.clip(Modifier.Companion, VerticalScrollableClipShape.INSTANCE) : ClipKt.clip(Modifier.Companion, HorizontalScrollableClipShape.INSTANCE));
    }
}
