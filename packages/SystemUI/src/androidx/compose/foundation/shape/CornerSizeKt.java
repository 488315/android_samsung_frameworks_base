package androidx.compose.foundation.shape;

import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public abstract class CornerSizeKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new CornerSize() { // from class: androidx.compose.foundation.shape.CornerSizeKt$ZeroCornerSize$1
            @Override // androidx.compose.foundation.shape.CornerSize
            /* renamed from: toPx-TmRCtEA */
            public final float mo185toPxTmRCtEA(Density density, long j) {
                return 0.0f;
            }

            public final String toString() {
                return "ZeroCornerSize";
            }
        };
    }

    public static final CornerSize CornerSize(int i) {
        return new PercentCornerSize(i);
    }

    /* renamed from: CornerSize-0680j_4, reason: not valid java name */
    public static final CornerSize m186CornerSize0680j_4(float f) {
        return new DpCornerSize(f, null);
    }
}
