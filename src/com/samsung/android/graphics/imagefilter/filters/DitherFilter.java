package com.samsung.android.graphics.imagefilter.filters;

import android.graphics.RuntimeShader;
import com.samsung.android.graphics.imagefilter.FilterEffect;

/* loaded from: classes6.dex */
public final class DitherFilter extends FilterEffect {
    private static final String TAG = "DitherFilter";
    private static final String declareCode = "uniform int enable;\n";
    private static final String functionCode = "float random(vec2 st) { \n   return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453123);\n}\nfloat n2rand_faster(vec2 n, float k) {\n     float nrnd0 = random( n );\n     float orig = k * (nrnd0 * 2.0 - 1.0);\n     nrnd0 = orig * inversesqrt(abs(orig));\n     nrnd0 = max(-k, nrnd0);\n     nrnd0 = k * (nrnd0 - sign(orig));\n     return nrnd0;\n}\nvec3 randomDither(vec2 uv, vec3 col) {\n    float bitError = 1.0/255.0;\n    float r = n2rand_faster(uv, 1.0);\n    return col + vec3(r * bitError);\n}\n";
    private static final String mainCode = "{\n    if (enable == 1) {\n        vec3 bandedColor = sampledColor.rgb;\n        vec3 ditheredColor = randomDither(fragCoord, bandedColor.rgb);\n        sampledColor = vec4(ditheredColor, sampledColor.a);\n    }\n}\n";
    private int enable;
    private final RuntimeShader shader;

    public DitherFilter() {
        setFilterType(4);
        this.enable = 0;
        this.shader = new RuntimeShader(assembleShaderCode(functionCode, declareCode, mainCode));
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
    public String getFunctionShaderCode() {
        return functionCode;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void setParam(int i, float f) {
        if (i == 7) {
            this.enable = f == 1.0f ? 1 : 0;
        }
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public float getParam(int i) {
        return this.enable == 1 ? 1.0f : 0.0f;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public RuntimeShader getShader() {
        return this.shader;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void updateShader(RuntimeShader runtimeShader) {
        runtimeShader.setIntUniform("enable", this.enable);
    }
}
