package androidx.compose.ui.text;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.unit.TextUnit;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Bullet implements AnnotatedString.Annotation {
    public final float alpha;
    public final Brush brush;
    public final DrawStyle drawStyle;
    public final long padding;
    public final Shape shape;
    public final long size;

    public /* synthetic */ Bullet(Shape shape, long j, long j2, Brush brush, float f, DrawStyle drawStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(shape, j, j2, brush, f, drawStyle);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof Bullet)) {
            Bullet bullet = (Bullet) obj;
            return Intrinsics.areEqual(this.shape, bullet.shape) && TextUnit.m866equalsimpl0(this.size, bullet.size) && TextUnit.m866equalsimpl0(this.padding, bullet.padding) && Intrinsics.areEqual(this.brush, bullet.brush) && this.alpha == bullet.alpha && Intrinsics.areEqual(this.drawStyle, bullet.drawStyle);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = this.shape.hashCode() * 31;
        TextUnit.Companion companion = TextUnit.Companion;
        int m = MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.size), 31, this.padding);
        Brush brush = this.brush;
        return this.drawStyle.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, (m + (brush != null ? brush.hashCode() : 0)) * 31, 31);
    }

    public final String toString() {
        return "Bullet(shape=" + this.shape + ", size=" + ((Object) TextUnit.m870toStringimpl(this.size)) + ", padding=" + ((Object) TextUnit.m870toStringimpl(this.padding)) + ", brush=" + this.brush + ", alpha=" + this.alpha + ", drawStyle=" + this.drawStyle + ')';
    }

    private Bullet(Shape shape, long j, long j2, Brush brush, float f, DrawStyle drawStyle) {
        this.shape = shape;
        this.size = j;
        this.padding = j2;
        this.brush = brush;
        this.alpha = f;
        this.drawStyle = drawStyle;
    }
}
