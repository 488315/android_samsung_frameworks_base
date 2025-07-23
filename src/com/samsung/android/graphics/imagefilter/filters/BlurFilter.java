package com.samsung.android.graphics.imagefilter.filters;

import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import com.samsung.android.graphics.imagefilter.FilterEffect;

/* loaded from: classes6.dex */
public final class BlurFilter extends FilterEffect {
    private static final String TAG = "BlurFilter";
    private float radius;

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public RuntimeShader getShader() {
        return null;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void updateShader(RuntimeShader runtimeShader) {
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public boolean useShaderCode() {
        return false;
    }

    public BlurFilter() {
        setFilterType(1);
        this.radius = 0.0f;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public String getMainShaderCode() {
        return "";
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public RenderEffect getRenderEffect() {
        float f = this.radius;
        return RenderEffect.createBlurEffect(f, f, Shader.TileMode.CLAMP);
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void setParam(int i, float f) {
        if (i == 0) {
            float f2 = f / 2.5f;
            this.radius = f2;
            if (f2 < 1.0f) {
                this.radius = 0.0f;
            }
        }
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public float getParam(int i) {
        return this.radius;
    }
}
