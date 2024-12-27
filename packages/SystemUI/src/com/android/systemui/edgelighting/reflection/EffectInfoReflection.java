package com.android.systemui.edgelighting.reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
