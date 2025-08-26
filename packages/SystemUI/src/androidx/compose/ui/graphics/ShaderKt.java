package androidx.compose.ui.graphics;

import android.graphics.Shader;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
public abstract class ShaderKt {
    /* renamed from: RadialGradientShader-8uybcMk, reason: not valid java name */
    public static final Shader m499RadialGradientShader8uybcMk(List list, List list2, long j, float f, int i) {
        AndroidShader_androidKt.validateColorStops(list, list2);
        GradientColorLongVerifier gradientColorLongVerifier = GradientColorLongVerifier.INSTANCE;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i2 = 0; i2 < size; i2++) {
            jArr[i2] = ((Color) list.get(i2)).value;
        }
        return gradientColorLongVerifier.m476createRadialGradientColorLong8uybcMk(j, f, jArr, list2 != null ? CollectionsKt___CollectionsKt.toFloatArray(list2) : null, i);
    }
}
