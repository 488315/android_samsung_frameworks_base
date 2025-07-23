package com.android.systemui.edgelighting.reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EffectInfoReflection extends AbstractBaseReflection {
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
