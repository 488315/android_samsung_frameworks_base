package androidx.compose.ui.graphics;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ColorMatrix {
    public final float[] values;

    private /* synthetic */ ColorMatrix(float[] fArr) {
        this.values = fArr;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ColorMatrix m468boximpl(float[] fArr) {
        return new ColorMatrix(fArr);
    }

    /* renamed from: constructor-impl$default, reason: not valid java name */
    public static float[] m469constructorimpl$default() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ColorMatrix) {
            return Intrinsics.areEqual(this.values, ((ColorMatrix) obj).values);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.values);
    }

    public final String toString() {
        return "ColorMatrix(values=" + Arrays.toString(this.values) + ')';
    }
}
