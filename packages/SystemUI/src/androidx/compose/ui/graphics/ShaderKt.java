package androidx.compose.ui.graphics;

import android.graphics.Shader;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ShaderKt {
    /* renamed from: RadialGradientShader-8uybcMk, reason: not valid java name */
    public static final Shader m497RadialGradientShader8uybcMk(List list, List list2, long j, float f, int i) {
        AndroidShader_androidKt.validateColorStops(list, list2);
        GradientColorLongVerifier gradientColorLongVerifier = GradientColorLongVerifier.INSTANCE;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i2 = 0; i2 < size; i2++) {
            jArr[i2] = ((Color) list.get(i2)).value;
        }
        return gradientColorLongVerifier.m474createRadialGradientColorLong8uybcMk(j, f, jArr, list2 != null ? CollectionsKt___CollectionsKt.toFloatArray(list2) : null, i);
    }
}
