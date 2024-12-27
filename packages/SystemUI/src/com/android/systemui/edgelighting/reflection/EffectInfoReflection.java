package com.android.systemui.edgelighting.reflection;

public final class EffectInfoReflection extends AbstractBaseReflection {
    public final Object mInstance;

    public EffectInfoReflection(Class<?> cls) {
        super(cls);
        this.mInstance = createInstance(null, new Object[0]);
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
    public final String getBaseClassName() {
        return "com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo";
    }
}
