package com.android.systemui.media.mediaoutput.compose.ext;

import android.graphics.Shader;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.ShaderKt;
import androidx.compose.ui.graphics.TileMode;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public final class BrushExtKt$radialGradientShader$1 extends ShaderBrush {
    public final /* synthetic */ List $colors;

    public BrushExtKt$radialGradientShader$1(List<Color> list) {
        this.$colors = list;
    }

    @Override // androidx.compose.ui.graphics.ShaderBrush
    /* renamed from: createShader-uvyYCjk */
    public final Shader mo453createShaderuvyYCjk(long j) {
        float fMax = Math.max(Float.intBitsToFloat((int) (4294967295L & j)), Float.intBitsToFloat((int) (j >> 32)));
        List listAsList = Arrays.asList(Float.valueOf(0.0f), Float.valueOf(0.5f));
        List list = this.$colors;
        TileMode.Companion.getClass();
        return ShaderKt.m499RadialGradientShader8uybcMk(list, listAsList, SizeKt.m422getCenteruvyYCjk(j), fMax * 1.1f * 2.0f, 0);
    }
}
