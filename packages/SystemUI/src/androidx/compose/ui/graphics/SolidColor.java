package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SolidColor extends Brush {
    public final long value;

    public /* synthetic */ SolidColor(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // androidx.compose.ui.graphics.Brush
    /* renamed from: applyTo-Pq9zytI */
    public final void mo449applyToPq9zytI(float f, long j, Paint paint) {
        AndroidPaint androidPaint = (AndroidPaint) paint;
        androidPaint.setAlpha(1.0f);
        long j2 = this.value;
        if (f != 1.0f) {
            j2 = ColorKt.Color(Color.m461getRedimpl(j2), Color.m460getGreenimpl(j2), Color.m458getBlueimpl(j2), Color.m457getAlphaimpl(j2) * f, Color.m459getColorSpaceimpl(j2));
        }
        androidPaint.m438setColor8_81llA(j2);
        if (androidPaint.internalShader != null) {
            androidPaint.setShader(null);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SolidColor)) {
            return false;
        }
        long j = ((SolidColor) obj).value;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.value, j);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "SolidColor(value=" + ((Object) Color.m462toStringimpl(this.value)) + ')';
    }

    private SolidColor(long j) {
        super(null);
        this.value = j;
    }
}
