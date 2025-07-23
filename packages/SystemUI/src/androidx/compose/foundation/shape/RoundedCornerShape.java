package androidx.compose.foundation.shape;

import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RoundedCornerShape extends CornerBasedShape {
    public RoundedCornerShape(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
        super(cornerSize, cornerSize2, cornerSize3, cornerSize4);
    }

    @Override // androidx.compose.foundation.shape.CornerBasedShape
    public final RoundedCornerShape copy(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
        return new RoundedCornerShape(cornerSize, cornerSize2, cornerSize3, cornerSize4);
    }

    @Override // androidx.compose.foundation.shape.CornerBasedShape
    /* renamed from: createOutline-LjSzlW0 */
    public final Outline mo183createOutlineLjSzlW0(long j, float f, float f2, float f3, float f4, LayoutDirection layoutDirection) {
        if (f + f2 + f3 + f4 == 0.0f) {
            return new Outline.Rectangle(SizeKt.m421toRectuvyYCjk(j));
        }
        Rect m421toRectuvyYCjk = SizeKt.m421toRectuvyYCjk(j);
        LayoutDirection layoutDirection2 = LayoutDirection.Ltr;
        float f5 = layoutDirection == layoutDirection2 ? f : f2;
        long floatToRawIntBits = (Float.floatToRawIntBits(f5) << 32) | (Float.floatToRawIntBits(f5) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        float f6 = layoutDirection == layoutDirection2 ? f2 : f;
        long floatToRawIntBits2 = (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(f6) & 4294967295L);
        float f7 = layoutDirection == layoutDirection2 ? f3 : f4;
        long floatToRawIntBits3 = (Float.floatToRawIntBits(f7) << 32) | (Float.floatToRawIntBits(f7) & 4294967295L);
        float f8 = layoutDirection == layoutDirection2 ? f4 : f3;
        return new Outline.Rounded(new RoundRect(m421toRectuvyYCjk.left, m421toRectuvyYCjk.top, m421toRectuvyYCjk.right, m421toRectuvyYCjk.bottom, floatToRawIntBits, floatToRawIntBits2, floatToRawIntBits3, (Float.floatToRawIntBits(f8) << 32) | (Float.floatToRawIntBits(f8) & 4294967295L), null));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoundedCornerShape)) {
            return false;
        }
        RoundedCornerShape roundedCornerShape = (RoundedCornerShape) obj;
        if (!Intrinsics.areEqual(this.topStart, roundedCornerShape.topStart)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.topEnd, roundedCornerShape.topEnd)) {
            return false;
        }
        if (Intrinsics.areEqual(this.bottomEnd, roundedCornerShape.bottomEnd)) {
            return Intrinsics.areEqual(this.bottomStart, roundedCornerShape.bottomStart);
        }
        return false;
    }

    public final int hashCode() {
        return this.bottomStart.hashCode() + ((this.bottomEnd.hashCode() + ((this.topEnd.hashCode() + (this.topStart.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "RoundedCornerShape(topStart = " + this.topStart + ", topEnd = " + this.topEnd + ", bottomEnd = " + this.bottomEnd + ", bottomStart = " + this.bottomStart + ')';
    }
}
