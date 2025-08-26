package androidx.compose.ui.graphics;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ColorMatrix {
    public final float[] values;

    private /* synthetic */ ColorMatrix(float[] fArr) {
        this.values = fArr;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ColorMatrix m470boximpl(float[] fArr) {
        return new ColorMatrix(fArr);
    }

    /* renamed from: constructor-impl$default, reason: not valid java name */
    public static float[] m471constructorimpl$default() {
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
