package androidx.compose.foundation;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BorderStroke {
    public final Brush brush;
    public final float width;

    public /* synthetic */ BorderStroke(float f, Brush brush, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, brush);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BorderStroke)) {
            return false;
        }
        BorderStroke borderStroke = (BorderStroke) obj;
        return Dp.m836equalsimpl0(this.width, borderStroke.width) && Intrinsics.areEqual(this.brush, borderStroke.brush);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return this.brush.hashCode() + (Float.hashCode(this.width) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BorderStroke(width=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.width, ", brush=", sb);
        sb.append(this.brush);
        sb.append(')');
        return sb.toString();
    }

    private BorderStroke(float f, Brush brush) {
        this.width = f;
        this.brush = brush;
    }
}
