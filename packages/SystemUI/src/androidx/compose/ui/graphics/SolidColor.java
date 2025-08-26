package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SolidColor extends Brush {
    public final long value;

    public /* synthetic */ SolidColor(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // androidx.compose.ui.graphics.Brush
    /* renamed from: applyTo-Pq9zytI */
    public final void mo451applyToPq9zytI(float f, long j, Paint paint) {
        AndroidPaint androidPaint = (AndroidPaint) paint;
        androidPaint.setAlpha(1.0f);
        long jColor = this.value;
        if (f != 1.0f) {
            jColor = ColorKt.Color(Color.m463getRedimpl(jColor), Color.m462getGreenimpl(jColor), Color.m460getBlueimpl(jColor), Color.m459getAlphaimpl(jColor) * f, Color.m461getColorSpaceimpl(jColor));
        }
        androidPaint.m440setColor8_81llA(jColor);
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
        return ULong.m3447equalsimpl0(this.value, j);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "SolidColor(value=" + ((Object) Color.m464toStringimpl(this.value)) + ')';
    }

    private SolidColor(long j) {
        super(null);
        this.value = j;
    }
}
