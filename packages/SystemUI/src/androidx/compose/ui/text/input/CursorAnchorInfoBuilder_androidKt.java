package androidx.compose.ui.text.input;

import androidx.compose.ui.geometry.Rect;

/* loaded from: classes.dex */
public abstract class CursorAnchorInfoBuilder_androidKt {
    public static final boolean containsInclusive(Rect rect, float f, float f2) {
        return f <= rect.right && rect.left <= f && f2 <= rect.bottom && rect.top <= f2;
    }
}
