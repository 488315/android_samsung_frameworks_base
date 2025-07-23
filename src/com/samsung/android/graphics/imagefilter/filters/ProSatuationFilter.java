package com.samsung.android.graphics.imagefilter.filters;

import android.graphics.RuntimeShader;
import com.samsung.android.graphics.imagefilter.FilterEffect;

/* loaded from: classes6.dex */
public final class ProSatuationFilter extends FilterEffect {
    private static final String TAG = "ProSatuationFilter";
    private static final String declareCode = "uniform float uProSaturation;\n";
    private static final String mainCode = "{\n   float epsilon = 0.0001;\n   float alpha = sampledColor.a;\n   float maxChn = max(max(sampledColor.r, sampledColor.g), sampledColor.b);\n   float minChn = min(min(sampledColor.r, sampledColor.g), sampledColor.b);\n   float clampSaturation = min(uProSaturation, maxChn / max((maxChn - minChn), epsilon));\n   sampledColor.rgb = vec3( min(maxChn-(maxChn - sampledColor.r) * clampSaturation, 1.0),        min(maxChn - (maxChn - sampledColor.g) * clampSaturation, 1.0),       min(maxChn - (maxChn - sampledColor.b) * clampSaturation, 1.0));\n   sampledColor.a = alpha; \n}\n";
    private float saturation;
    private final RuntimeShader shader;

    public ProSatuationFilter() {
        setFilterType(8);
        this.shader = new RuntimeShader(assembleShaderCode("", declareCode, mainCode));
        this.saturation = 0.0f;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public String getMainShaderCode() {
        return mainCode;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public String getDeclareShaderCode() {
        return declareCode;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void setParam(int i, float f) {
        if (i != 1) {
            return;
        }
        this.saturation = f;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public float getParam(int i) {
        return this.saturation;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void updateShader(RuntimeShader runtimeShader) {
        runtimeShader.setFloatUniform("uProSaturation", Math.round(((Math.min(this.saturation, 0.0f) + 1.0f) + (((float) Math.pow(Math.max(this.saturation, 0.0f), 2.0d)) * 4.0f)) * 100.0f) / 100.0f);
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public RuntimeShader getShader() {
        return this.shader;
    }
}
