package androidx.compose.ui.graphics.painter;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ColorPainter extends Painter {
    public float alpha;
    public final long color;
    public ColorFilter colorFilter;
    public final long intrinsicSize;

    public /* synthetic */ ColorPainter(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyAlpha(float f) {
        this.alpha = f;
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorPainter)) {
            return false;
        }
        long j = ((ColorPainter) obj).color;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.color, j);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo561getIntrinsicSizeNHjbRc() {
        return this.intrinsicSize;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.color);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        DrawScope.m539drawRectnJ9OG0$default(drawScope, this.color, 0L, 0L, this.alpha, null, this.colorFilter, 0, 86);
    }

    public final String toString() {
        return "ColorPainter(color=" + ((Object) Color.m462toStringimpl(this.color)) + ')';
    }

    private ColorPainter(long j) {
        this.color = j;
        this.alpha = 1.0f;
        Size.Companion.getClass();
        this.intrinsicSize = Size.Unspecified;
    }
}
