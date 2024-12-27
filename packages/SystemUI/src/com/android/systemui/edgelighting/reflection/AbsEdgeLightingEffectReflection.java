package com.android.systemui.edgelighting.reflection;

import android.content.Context;

public final class AbsEdgeLightingEffectReflection extends AbstractBaseReflection {
    public final ClassLoader mClassLoader;
    public final Object mInstance;

    public AbsEdgeLightingEffectReflection(Class<?> cls, Context context, Context context2, ClassLoader classLoader) {
        super(cls);
        this.mClassLoader = classLoader;
        this.mInstance = createInstance(new Class[]{Context.class, Context.class}, context, context2);
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
    public final String getBaseClassName() {
        return "com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect";
    }
}
