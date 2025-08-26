package androidx.compose.foundation.shape;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class RoundedCornerShapeKt {
    public static final RoundedCornerShape CircleShape;

    static {
        CornerSize CornerSize = CornerSizeKt.CornerSize(50);
        CircleShape = new RoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize);
    }

    public static final RoundedCornerShape RoundedCornerShape(float f, float f2, float f3, float f4) {
        int i = CornerSizeKt.$r8$clinit;
        return new RoundedCornerShape(new PxCornerSize(f), new PxCornerSize(f2), new PxCornerSize(f3), new PxCornerSize(f4));
    }

    /* renamed from: RoundedCornerShape-0680j_4, reason: not valid java name */
    public static final RoundedCornerShape m187RoundedCornerShape0680j_4(float f) {
        CornerSize cornerSizeM186CornerSize0680j_4 = CornerSizeKt.m186CornerSize0680j_4(f);
        return new RoundedCornerShape(cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4);
    }

    /* renamed from: RoundedCornerShape-a9UjIt4, reason: not valid java name */
    public static final RoundedCornerShape m188RoundedCornerShapea9UjIt4(float f, float f2, float f3, float f4) {
        return new RoundedCornerShape(CornerSizeKt.m186CornerSize0680j_4(f), CornerSizeKt.m186CornerSize0680j_4(f2), CornerSizeKt.m186CornerSize0680j_4(f3), CornerSizeKt.m186CornerSize0680j_4(f4));
    }

    /* renamed from: RoundedCornerShape-a9UjIt4$default, reason: not valid java name */
    public static RoundedCornerShape m189RoundedCornerShapea9UjIt4$default(float f, float f2, float f3, float f4, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        if ((i & 4) != 0) {
            f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        if ((i & 8) != 0) {
            f4 = 0;
            Dp.Companion companion4 = Dp.Companion;
        }
        return m188RoundedCornerShapea9UjIt4(f, f2, f3, f4);
    }
}
