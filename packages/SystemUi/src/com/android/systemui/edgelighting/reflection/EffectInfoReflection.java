package com.android.systemui.edgelighting.reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
