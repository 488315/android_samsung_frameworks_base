package com.samsung.android.graphics.imagefilter;

import android.graphics.RuntimeShader;

/* loaded from: classes6.dex */
public final class AssembledShader {
    private static final String TAG = "AssembledShader";
    private final boolean assemble;
    private final int filterMask;
    private final String name;
    private final RuntimeShader runtimeShader;
    private final String shaderString;

    public AssembledShader(int i, String str) {
        this.filterMask = i;
        this.shaderString = str;
        this.runtimeShader = new RuntimeShader(str);
        this.assemble = true;
        this.name = "CustomShader";
    }

    public AssembledShader(FilterEffect filterEffect) {
        this.filterMask = filterEffect.getFilterType();
        this.shaderString = "";
        this.assemble = false;
        this.runtimeShader = null;
        this.name = filterEffect.getFilterName();
    }

    public String getShader() {
        return this.shaderString;
    }

    public int getFilterMask() {
        return this.filterMask;
    }

    public RuntimeShader getRuntimeShader() {
        return this.runtimeShader;
    }

    public boolean isAssemble() {
        return this.assemble;
    }

    public String getName() {
        return this.name;
    }
}
