package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public final class ColumnScopeInstance implements ColumnScope {
    public static final ColumnScopeInstance INSTANCE = new ColumnScopeInstance();

    private ColumnScopeInstance() {
    }

    public final Modifier align(Modifier.Companion companion, BiasAlignment.Horizontal horizontal) {
        HorizontalAlignElement horizontalAlignElement = new HorizontalAlignElement(horizontal);
        companion.getClass();
        return horizontalAlignElement;
    }

    public final Modifier weight(Modifier modifier, float f, boolean z) {
        if (f <= 0.0d) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid weight; must be greater than zero");
        }
        if (f > Float.MAX_VALUE) {
            f = Float.MAX_VALUE;
        }
        return modifier.then(new LayoutWeightElement(f, z));
    }
}
