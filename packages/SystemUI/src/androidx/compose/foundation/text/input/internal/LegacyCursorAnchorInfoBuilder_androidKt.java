package androidx.compose.foundation.text.input.internal;

import androidx.compose.ui.geometry.Rect;

/* loaded from: classes.dex */
public abstract class LegacyCursorAnchorInfoBuilder_androidKt {
    public static final boolean containsInclusive(Rect rect, float f, float f2) {
        return f <= rect.right && rect.left <= f && f2 <= rect.bottom && rect.top <= f2;
    }
}
